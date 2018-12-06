package egovframework.search.inner;

import egovframework.search.FrequentlyAskedMenu;
import egovframework.search.common.WNCommon;
import egovframework.search.common.WNDefine;
import egovframework.search.common.WNUtils;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InnerSearchService {

    private static final Logger logger = LoggerFactory.getLogger(InnerSearchService.class);
    private static final String MULTI_GROUP_BY_FIELD = "ALIAS";
    private static final String MULTI_GROUP_BY_RESULT_SPLIT = "\\^\\^";

    private final static String groupByCode = "groupByCode";
    private final static String groupByCount = "groupByCount";
    private final static String groupByName = "groupByName";

    public enum GROUP_BY_CDOE {

        publicJobBbs("게시판"),
        publicAgencyJobs("공공기관채용정보"),
        careers("채용정보"),
        publicRecruitment("공채속보"),
        goodCompany("전남우수기업"),
        jobPolicyBus("청년희망버스"),
        jobPolicyMeet("구인구직만남의날"),
        jobPolicyCenter("찾아가는일자리상담센터"),
        jobPolicyDay("잡매칭데이"),
        eduOrgan("기관소개"),
        eduInfo("교육훈련정보"),
        eduBbs("게시판"),
        bussinessJang("사업장"),
        bussinessBbs("게시판"),
        miniJob("채용정보");

        private String text;
        GROUP_BY_CDOE(String text) {
            this.text = text;
        }
        public String getText() {
            return this.text;
        }
    }

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

            if(!WNUtils.isEmpty(group)) wnsearch.setCollectionInfoValue(collection, WNDefine.EXQUERY_FIELD,
                    String.format("<%s:contains:%s>", MULTI_GROUP_BY_FIELD, group));

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
            groupings = Arrays.asList(multiGroupByResultArray).stream().filter((String input) -> { // 좆같은건 버린다.

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

            }).collect(Collectors.toList());
            //groupings.forEach((Map<String, String> input) -> { logger.info(String.format("groupings %s, %s, %s",
            //        input.get(groupByCode), input.get(groupByCount), input.get(groupByName))); });

        }

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
                Map<String, String> documentMap = new HashMap<>();
                searchResultFieldList.stream().forEach((String field) -> {

                    field = field.split("/")[0];
                    String result = wnsearch.getField(collection, field, index, false);

                    result.replaceAll("&#8228;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&#8231;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;B&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;BR&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;/B&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;/BR&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;b&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;br&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;/b&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("&lt;/br&gt;", WNCommon.EMPTY_STRING);
                    result.replaceAll("<B>", WNCommon.EMPTY_STRING);
                    result.replaceAll("<BR>", WNCommon.EMPTY_STRING);
                    result.replaceAll("</B>", WNCommon.EMPTY_STRING);
                    result.replaceAll("</BR>", WNCommon.EMPTY_STRING);
                    result.replaceAll("<b>", WNCommon.EMPTY_STRING);
                    result.replaceAll("<br>", WNCommon.EMPTY_STRING);
                    result.replaceAll("</b>", WNCommon.EMPTY_STRING);
                    result.replaceAll("</br>", WNCommon.EMPTY_STRING);
                    result.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", WNCommon.EMPTY_STRING);

                    documentMap.put(field, result);

                });
                logger.info(String.format("[SEARCH::SERVICE] collection result count is => collection:%s,documentMap:%s", collection, documentMap));
                documentMapList.add(documentMap);

            });

            collectionResultMap.put(collection + "Result", documentMapList);
            logger.info(String.format("[SEARCH::SERVICE] collection result count is => collection:%s,count:%s,thisTotalCount:%s",
                    collection, count, thisTotalCount));

        });

        // 실시간 검색어
        String realTimeKeywordString = wnsearch.recvRealTimeSearchKeywordList(5);
        realTimeKeywordString = (realTimeKeywordString == null || "".equals(realTimeKeywordString)) ?
                wnsearch.realTimeKeywords : realTimeKeywordString;
        List<String> realTimeKeywords = realTimeKeywordString != null && realTimeKeywordString.split(",") != null &&
                !"".equals(realTimeKeywordString) ? new ArrayList<>(Arrays.asList(realTimeKeywordString.split(",")))
                : new ArrayList<>();

        // 전체 결과
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
            frequentlyAskedMenus.forEach((FrequentlyAskedMenu input) -> { logger.info(String.format("frequentlyAskedMenu %s, %s",
                    input.getMenuNm(), input.getMenuUrl())); });

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            return frequentlyAskedMenus;
        }

    }

}
