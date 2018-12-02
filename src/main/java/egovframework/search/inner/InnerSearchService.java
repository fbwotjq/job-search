package egovframework.search.inner;

import egovframework.search.common.WNCommon;
import egovframework.search.common.WNDefine;
import egovframework.search.outer.common.WNSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InnerSearchService {

    private static final Logger logger = LoggerFactory.getLogger(InnerSearchService.class);

    public Map<String,Object> search(String query, String[] collections, int startCount, int viewResultCount) {

        List<String> collectionNameList = new ArrayList<>(Arrays.asList(collections));
        logger.info(String.format("[SEARCH::SERVICE] PARAM DEBUG MESSAGE => %s,%s,%s,%s", query, collections, startCount, viewResultCount));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 검색 조건 셋팅, 질의, 디버그
        WNSearch wnsearch = new WNSearch(WNCommon.IS_DEBUG, WNCommon.IS_UID_SEARCH, collections, null, 0);
        collectionNameList.stream().forEach((String collection) -> {

            wnsearch.setCollectionInfoValue(collection, WNDefine.PAGE_INFO, String.format("%s,%s", startCount, viewResultCount));
            wnsearch.setCollectionInfoValue(collection, WNDefine.SORT_FIELD, "RANK/DESC");

        });
        wnsearch.search(query, !WNCommon.IS_REALTIME_KEYWORD, WNDefine.CONNECTION_CLOSE, WNCommon.USE_SUGGESTED_QUERY);

        String debugMsg = wnsearch.printDebug() != null ? wnsearch.printDebug().trim() : "";
        logger.info(String.format("[SEARCH::SERVICE] CONDITION DEBUG MESSAGE => %s", debugMsg));

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 검색 결과 정리

        // 컬랙션별 결과
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Integer> collectionCountMap = new HashMap<>();
        Map<String, Object> collectionResultMap = new HashMap<>();

        if ( wnsearch != null ) {
            wnsearch.closeServer();
        }

        //resultMap.put("totalCount", totalCount);
        //resultMap.put("lastPaging", lastPaging);
        resultMap.put("collectionCountMap", collectionCountMap);
        resultMap.put("collectionResultMap", collectionResultMap);
        //resultMap.put("paging", paging);

        return resultMap;

    }

}
