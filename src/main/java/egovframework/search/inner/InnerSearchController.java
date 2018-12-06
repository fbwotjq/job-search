package egovframework.search.inner;

import egovframework.search.FrequentlyAskedMenu;
import egovframework.search.common.WNCommon;
import egovframework.search.inner.common.WNCollection;
import egovframework.search.outer.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inner/search")
public class InnerSearchController {

    private static final Logger logger = LoggerFactory.getLogger(InnerSearchController.class);

    @Autowired InnerSearchService innerSearchService;
    @Autowired SearchService searchService;

    @RequestMapping(value = "/search.do", method={RequestMethod.GET, RequestMethod.POST})
    public ModelAndView search(
        @RequestParam(value = "query", defaultValue = WNCommon.EMPTY_STRING, required = false) String query,
        @RequestParam(value = "collection", defaultValue = WNCommon.COLLECTION_ALL, required = false) String collection,
        @RequestParam(value = "startCount", defaultValue = WNCommon.ZERO, required = false) int startCount,
        @RequestParam(value = "group", defaultValue = WNCommon.EMPTY_STRING, required = false) String group
    ) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search/inner/search");
        modelAndView.addObject("query", query);
        modelAndView.addObject("collection", collection);
        modelAndView.addObject("group", group);

        int viewCount = WNCommon.COLLECTION_ALL.equals(collection) ? 3 : 10;
        String[] collections = WNCommon.COLLECTION_ALL.equals(collection) ? WNCollection.COLLECTIONS
                : new String[] { collection };
        logger.info(String.format("[SEARCH::CONTROLLER] PARAM DEBUG MESSAGE => %s,%s,%s,%s", query, collection, startCount, group));

        try {

            Map<String, Object> result = innerSearchService.search(query, collections, group, startCount, viewCount);

            int totalCount = (int) result.get("totalCount");
            String paging = (String) result.get("paging");
            int lastPaging = (int) result.get("lastPaging");
            List<String> realTimeKeywords = (List<String>) result.get("realTimeKeywords");
            List<Map<String, String>> groupings = (List<Map<String, String>>) result.get("groupings");

            Map<String, Integer> collectionCountMap = (Map<String, Integer>) result.get("collectionCountMap");
            Map<String, Object> collectionResultMap = (Map<String, Object>) result.get("collectionResultMap");

            List<String> weeklyPopKeywords = searchService.getPopKeyword("_ALL_", "W");
            List<String> dailyPopKeywords = searchService.getPopKeyword("_ALL_", "D");
            List<FrequentlyAskedMenu> frequentlyAskedMenus = innerSearchService.getFrequentlyAskedMenus();

            modelAndView.addObject("totalCount", totalCount);
            modelAndView.addObject("lastPaging", lastPaging);
            modelAndView.addObject("collectionCountMap", collectionCountMap);
            modelAndView.addObject("collectionResultMap", collectionResultMap);
            modelAndView.addObject("paging", paging);
            modelAndView.addObject("weeklyPopKeywords", weeklyPopKeywords);
            modelAndView.addObject("dailyPopKeywords", dailyPopKeywords);
            modelAndView.addObject("realTimeKeywords", realTimeKeywords);
            modelAndView.addObject("groupingsResults", groupings);
            modelAndView.addObject("frequentlyAskedMenus", frequentlyAskedMenus);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            return modelAndView;
        }

    }

}
