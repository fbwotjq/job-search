package egovframework.search.outer;

import egovframework.search.common.WNCommon;
import egovframework.search.outer.common.WNCollection;
import egovframework.search.common.WNDefine;
import egovframework.search.outer.common.WNSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class SearchService {

	private final static String POP_KEYWORDURI_PATH = "/manager/WNRun.do?target=popword&collection=_ALL_&range=M";

	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

	public Map<String, Object> search(
		String query,
		String[] collections,
		int startCount,
		int viewResultCount
	) {
		
		List<String> collectionNameList = new ArrayList<>(Arrays.asList(collections));
		logger.info(String.format("[SEARCH::SERVICE] PARAM DEBUG MESSAGE => %s,%s,%s,%s", query, collections, startCount, viewResultCount));
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 검색 조건 셋팅, 질의, 디버그 
		WNSearch wnsearch = new WNSearch(WNCommon.IS_DEBUG, WNCommon.IS_UID_SEARCH, collections, null, 0);
		collectionNameList.stream().forEach((String collection) -> {
			
			wnsearch.setCollectionInfoValue(collection, WNDefine.PAGE_INFO, String.format("%s,%s", startCount, viewResultCount));
			wnsearch.setCollectionInfoValue(collection, WNDefine.SORT_FIELD, "RANK/DESC");
			
		});
		wnsearch.search(query, WNCommon.IS_REALTIME_KEYWORD, WNDefine.CONNECTION_CLOSE, WNCommon.USE_SUGGESTED_QUERY);
		
		String debugMsg = wnsearch.printDebug() != null ? wnsearch.printDebug().trim() : "";
		logger.info(String.format("[SEARCH::SERVICE] CONDITION DEBUG MESSAGE => %s", debugMsg));

		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 검색 결과 정리
		
		// 컬랙션별 결과
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Integer> collectionCountMap = new HashMap<>();
		Map<String, Object> collectionResultMap = new HashMap<>();
		collectionNameList.stream().forEach((String collection) -> {
			
			int count = wnsearch.getResultTotalCount(collection);
			collectionCountMap.put(collection + "Count", count);
			
			int thisTotalCount = wnsearch.getResultCount(collection);
			
			List<Map<String, String>> documentMapList = new ArrayList<Map<String, String>>();
			IntStream.range(0, thisTotalCount).forEach((int index) -> {
				
				List<String> searchResultFieldList = wnsearch.getSearchResultField(collection);
				//if(index == 0) { logger.info(String.format("[SEARCH::SERVICE] result field is => %s", searchResultFieldList)); }
				
				Map<String, String> documentMap = new HashMap<>();
				searchResultFieldList.stream().forEach((String field) -> {
					
					field = field.split("/")[0];
					String result = wnsearch.getField(collection, field, index, false);

					result.replaceAll("&#8228;", "");
					result.replaceAll("&#8231;", "");
					result.replaceAll("&lt;B&gt;", "");
					result.replaceAll("&lt;BR&gt;", "");
					result.replaceAll("&lt;/B&gt;", "");
					result.replaceAll("&lt;/BR&gt;", "");
					result.replaceAll("&lt;b&gt;", "");
					result.replaceAll("&lt;br&gt;", "");
					result.replaceAll("&lt;/b&gt;", "");
					result.replaceAll("&lt;/br&gt;", "");
					result.replaceAll("<B>", "");
					result.replaceAll("<BR>", "");
					result.replaceAll("</B>", "");
					result.replaceAll("</BR>", "");
					result.replaceAll("<b>", "");
					result.replaceAll("<br>", "");
					result.replaceAll("</b>", "");
					result.replaceAll("</br>", "");
					result.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

					documentMap.put(field, result);
					
				});
				documentMapList.add(documentMap);
				
			});
			collectionResultMap.put(collection + "Result", documentMapList);
			logger.info(String.format("[SEARCH::SERVICE] collection result count is => collection:%s,count:%s,thisTotalCount:%s", 
					collection, count, thisTotalCount));
			
		});
		
		// 전체 결과
		int totalCount = collectionCountMap.entrySet().stream().mapToInt(map -> map.getValue()).sum();
		int lastPaging = totalCount == 0 ? 0 : (int)Math.floor(totalCount / 10) * 10;
		String paging = collectionNameList.size() == 1 ? wnsearch.getPageLinks(startCount, totalCount, viewResultCount, 5) : "";
		
		logger.info(String.format("[SEARCH::SERVICE] RESULT DEBUG MESSAGE => totalCount: %s, collectionCountMap:%s, paging: %s", 
				totalCount, collectionCountMap, paging));
		
		if ( wnsearch != null ) {
			wnsearch.closeServer();
		}
		
		resultMap.put("totalCount", totalCount);
		resultMap.put("lastPaging", lastPaging);
		resultMap.put("collectionCountMap", collectionCountMap);
		resultMap.put("collectionResultMap", collectionResultMap);
		resultMap.put("paging", paging);
		
		return resultMap;
		
	}

	public List<String> getPopKeyword() {

		List<String> keywords = new ArrayList<>();

		try {

			RestTemplate restTemplate = new RestTemplate();
			URI uri = URI.create(String.format("http://%s:%s%s", WNCollection.MANAGER_IP, WNCollection.MANAGER_PORT, POP_KEYWORDURI_PATH));
			String responseString = restTemplate.getForObject(uri, String.class);

			if(responseString != null && !responseString.equals("")) {

				StringReader stringReader = new StringReader(responseString);
				InputSource inputSource = new InputSource(stringReader);
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
				//logger.info(String.format("[SEARCH::POPKEYWORD] document => %s", document.getTextContent()));

				if(document != null) {
					NodeList rootChildNodelist = document.getChildNodes();

					if(rootChildNodelist != null) {
						Node dataNode = rootChildNodelist.item(0);
						//logger.info(String.format("[SEARCH::POPKEYWORD] dataNode => %s", dataNode.getTextContent()));

						if(dataNode != null) {
							NodeList dataChildNode = dataNode.getChildNodes();

							if(dataChildNode != null) {

								int currentSize = dataChildNode.getLength();
								for( int i = 0 ; i < currentSize ; i++ ) {

									Node node = dataChildNode.item(i);
									//logger.info(String.format("[SEARCH::POPKEYWORD] dataChildNode => %s,%s", node.getNodeName(), node.getTextContent()));

									if(node != null && node.getNodeName().equals("Query")) {
										//logger.info(String.format("[SEARCH::POPKEYWORD] Query => %s", node.getTextContent()));
										String keyword = node.getTextContent();
										keywords.add(keyword);
									}

								}

							}
						}
					}
				}

			}

			logger.info(String.format("[SEARCH::POPKEYWORD] RESULT MESSAGE => %s, %s", responseString, keywords.size()));

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		} finally {
			return keywords;
		}

	}
	
}
