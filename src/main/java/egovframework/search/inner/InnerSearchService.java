package egovframework.search.inner;

import egovframework.search.FrequentlyAskedMenu;
import egovframework.search.common.WNCommon;
import egovframework.search.common.WNDefine;
import egovframework.search.common.WNUtils;
import egovframework.search.inner.common.GROUP_BY_CDOE;
import egovframework.search.inner.common.WNCollection;
import egovframework.search.inner.common.WNSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InnerSearchService {

    private static final Logger logger = LoggerFactory.getLogger(InnerSearchService.class);
    private static final String MULTI_GROUP_BY_FIELD = "ALIAS";
    private static final String MULTI_GROUP_BY_RESULT_SPLIT = "\\^\\^";
    private static final String SEARCH_FIELD_BY_RESULT_SPLIT = ",";

    private final static String groupByCode = "groupByCode";
    private final static String groupByCount = "groupByCount";
    private final static String groupByName = "groupByName";

    public Map<String,Object> search(
        String query,
        String[] collections,
        String group,
        int startCount,
        int viewResultCount
    ) throws Exception {

        List<String> collectionNameList = new ArrayList<>(Arrays.asList(collections));
        logger.info(String.format("[SEARCH::SERVICE] PARAM DEBUG MESSAGE => %s,%s,%s,%s", query, collections, startCount, viewResultCount));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 검색 조건 셋팅, 질의, 디버그
        WNSearch wnsearch = new WNSearch(WNCommon.IS_DEBUG, WNCommon.IS_UID_SEARCH, collections, null, 0);
        collectionNameList.stream().forEach((String collection) -> {

            if(collectionNameList.size() == 1 && !WNUtils.isEmpty(group)) wnsearch.setCollectionInfoValue(collection, WNDefine.EXQUERY_FIELD,
                    String.format("<%s:contains:%s>", MULTI_GROUP_BY_FIELD, group));

            if(collectionNameList.size() == 1 && WNUtils.isEmpty(group)) wnsearch.setCollectionInfoValue(collection,
                        WNDefine.GROUP_BY, String.format("%s,5", MULTI_GROUP_BY_FIELD));

            logger.info(String.format("collectionNameListSize:%s, isGroupEmpty:%s", collectionNameList.size(), WNUtils.isEmpty(group)));

            wnsearch.setCollectionInfoValue(collection, WNDefine.MULTI_GROUP_BY, MULTI_GROUP_BY_FIELD);
            wnsearch.setCollectionInfoValue(collection, WNDefine.PAGE_INFO, String.format("%s,%s", startCount, viewResultCount));
            wnsearch.setCollectionInfoValue(collection, WNDefine.SORT_FIELD, WNCommon.RANK_DESC);

        });
        wnsearch.search(query, !WNCommon.IS_REALTIME_KEYWORD, WNDefine.CONNECTION_CLOSE, WNCommon.USE_SUGGESTED_QUERY);

        String debugMsg = wnsearch.printDebug() != null ? wnsearch.printDebug().trim() : WNCommon.EMPTY_STRING;
        logger.info(String.format("[SEARCH::SERVICE] CONDITION DEBUG MESSAGE => %s", debugMsg));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 검색 결과 정리

        // 그룹바이에 대한 결과
        List<Map<String, String>> groupings = null;
        if(collectionNameList.size() == 1) {

            String collection = collectionNameList.get(0);
            String multiGroupByResult = wnsearch.getMultiGroupByResult(collection, MULTI_GROUP_BY_FIELD);
            String[] multiGroupByResultArray = WNUtils.isEmpty(multiGroupByResult) ? new String[0] : multiGroupByResult.split("@");

            Supplier<Stream<Map<String, String>>> streams = () -> Arrays.asList(multiGroupByResultArray).stream()
            .filter((String input) -> { // 혹시나 좆 같은건 버린다.

                String[] groupBys = input.split(MULTI_GROUP_BY_RESULT_SPLIT);
                GROUP_BY_CDOE value = GROUP_BY_CDOE.valueOf(groupBys[0]);
                return !WNUtils.isEmpty(input) && input.split(MULTI_GROUP_BY_RESULT_SPLIT).length == 2
                        && !WNUtils.isEmpty(groupBys[1]) && value != null;

            }).map((String input) -> {

                String[] groupBys = input.split(MULTI_GROUP_BY_RESULT_SPLIT);
                Map<String, String> map = new HashMap<>();
                map.put(groupByCode, groupBys[0]);
                map.put(groupByCount, groupBys[1]);
                map.put(groupByName, GROUP_BY_CDOE.valueOf(groupBys[0]).getText());
                return map;

            });

            final List<GROUP_BY_CDOE> matchedCodes = streams.get()
            .map((Map<String, String> result) -> GROUP_BY_CDOE.valueOf(result.get(groupByCode)))
            .collect(Collectors.toList());

            groupings = Stream.concat(streams.get(), Arrays.stream(GROUP_BY_CDOE.values())
            .filter((GROUP_BY_CDOE code) -> code.getCollection().equals(collection))
            .filter((GROUP_BY_CDOE code) ->
                    !matchedCodes.stream().filter((GROUP_BY_CDOE matchedCode) -> code == matchedCode)
                    .findAny().isPresent())
            .map((GROUP_BY_CDOE code) -> {

                Map<String, String> map = new HashMap<>();
                map.put(groupByCode, code.name());
                map.put(groupByCount, new Integer(0).toString());
                map.put(groupByName, code.getText());
                return map;

            })).collect(Collectors.toList());
            //groupings.forEach((Map<String, String> input) -> { logger.info(String.format("groupings %s, %s, %s",
            //        input.get(groupByCode), input.get(groupByCount), input.get(groupByName))); });

        }

        // 컬랙션별 검색된 도큐먼트 결과
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Integer> collectionCountMap = new HashMap<>();
        Map<String, Object> collectionResultMap = new HashMap<>();
        List<GroupResult> collectionGroupResultMap = null;

        if(collectionNameList.size() == 1 && WNUtils.isEmpty(group)) {

            String collection = collectionNameList.get(0);

            int count = wnsearch.getResultTotalCount(collection);
            collectionCountMap.put(collection + "Count", count);

            int matchedGroupCount = wnsearch.getResultTotalGroupCount(collection);
            logger.info(String.format("resultTotalCount: %s, matchedGroupCount: %s", count, matchedGroupCount));
            List<String> searchResultFieldList = wnsearch.getSearchResultField(collection);

            collectionGroupResultMap = IntStream.range(0, matchedGroupCount).mapToObj((int index) -> {

                int matchedGroupResultTotalCount = wnsearch.getTotalCountInGroup(collection, index);
                int matchedGroupResultCount = wnsearch.getCountInGroup(collection, index);

                logger.info(String.format("matchedGroupResultTotalCount:%s, matchedGroupResultCount:%s", matchedGroupResultTotalCount, matchedGroupResultCount));

                List<Map<String, String>> documents = IntStream.range(0, matchedGroupResultCount).mapToObj((int resultIndex) -> {
                    Map<String, String> document = searchResultFieldList.stream()
                    .map((String field) -> field.split("/")[0])
                    .collect(Collectors.toMap(
                        (String field) -> field,
                        (String field) -> WNUtils.replaceHtml(wnsearch.getFieldInGroup(collection, field, index, resultIndex)
                    )));
                    logger.info(String.format("[SEARCH::SERVICE] collection grouped result => collection:%s, document:%s", collection, document));
                    return document;
                }).collect(Collectors.toList());

                GroupResult groupResult = new GroupResult();
                groupResult.setAlias(documents.get(0).get(MULTI_GROUP_BY_FIELD));
                groupResult.setAliasText(GROUP_BY_CDOE.valueOf(documents.get(0).get(MULTI_GROUP_BY_FIELD)).getText());
                groupResult.setDocuments(documents);
                groupResult.setCount(matchedGroupResultTotalCount);
                return groupResult;

            }).collect(Collectors.toList());

        } else {

            collectionNameList.stream().forEach((String collection) -> {

                int count = wnsearch.getResultTotalCount(collection);
                collectionCountMap.put(collection + "Count", count);

                int thisTotalCount = wnsearch.getResultCount(collection);

                List<Map<String, String>> documentMapList = new ArrayList<Map<String, String>>();
                IntStream.range(0, thisTotalCount).forEach((int index) -> {

                    List<String> searchResultFieldList = wnsearch.getSearchResultField(collection);
                    Map<String, String> documentMap = new HashMap<>();
                    searchResultFieldList.stream().forEach((String field) -> {

                        field = field.split("/")[0];
                        String result = WNUtils.replaceHtml(wnsearch.getField(collection, field, index, false));
                        documentMap.put(field, result);

                    });
                    logger.info(String.format("[SEARCH::SERVICE] collection result count is => collection:%s,documentMap:%s", collection, documentMap));
                    documentMapList.add(documentMap);

                });

                collectionResultMap.put(collection + "Result", documentMapList);
                logger.info(String.format("[SEARCH::SERVICE] collection result count is => collection:%s,count:%s,thisTotalCount:%s",
                        collection, count, thisTotalCount));

            });

        }

        // 실시간 검색어
        String realTimeKeywordString = wnsearch.recvRealTimeSearchKeywordList(5);
        realTimeKeywordString = (realTimeKeywordString == null || WNCommon.EMPTY_STRING.equals(realTimeKeywordString)) ?
                wnsearch.realTimeKeywords : realTimeKeywordString;
        List<String> realTimeKeywords = realTimeKeywordString != null && realTimeKeywordString.split(",") != null &&
                !WNCommon.EMPTY_STRING.equals(realTimeKeywordString) ? new ArrayList<>(Arrays.asList(realTimeKeywordString.split(",")))
                : new ArrayList<>();

        // 전체 건수 결과
        int totalCount = collectionCountMap.entrySet().stream().mapToInt(map -> map.getValue()).sum();
        int lastPaging = totalCount == 0 ? 0 : (int)Math.floor(totalCount / 10) * 10;
        String paging = collectionNameList.size() == 1 ? wnsearch.getPageLinks(startCount, totalCount, viewResultCount,
                5) : WNCommon.EMPTY_STRING;

        logger.info(String.format("[SEARCH::SERVICE] RESULT DEBUG MESSAGE => totalCount: %s, collectionCountMap:%s, paging: %s, realTimeKeywordString: %s",
                totalCount, collectionCountMap, paging, realTimeKeywordString));

        if (wnsearch != null) {
            wnsearch.closeServer();
        }

        resultMap.put("totalCount", totalCount);
        resultMap.put("lastPaging", lastPaging);
        resultMap.put("collectionCountMap", collectionCountMap);
        resultMap.put("collectionResultMap", collectionResultMap);
        resultMap.put("paging", paging);
        resultMap.put("realTimeKeywords", realTimeKeywords);
        resultMap.put("groupings", groupings);
        resultMap.put("collectionGroupResultMap", collectionGroupResultMap);

        return resultMap;

    }

    public List<FrequentlyAskedMenu> getFrequentlyAskedMenus() {

        List<FrequentlyAskedMenu> frequentlyAskedMenus = null;
        try {

            String url = String.format("http://%s%s", WNCollection.SERVICE_DOMAIN, "/api/frequentMenuList.do?siteGroup=frequent&exceptMenuGroup=frequent&pageUnit=10");
            logger.info(String.format("CALL => %s", url));
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<FrequentlyAskedMenu>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<FrequentlyAskedMenu>>(){});
            frequentlyAskedMenus = response.getBody();
            //frequentlyAskedMenus.forEach((FrequentlyAskedMenu input) -> { logger.info(String.format("frequentlyAskedMenu %s, %s",
            //        input.getMenuNm(), input.getMenuUrl())); });

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            return frequentlyAskedMenus;
        }

    }

}
