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
import java.util.concurrent.CompletableFuture;

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
        modelAndView.addObject("serviceDomain", WNCollection.SERVICE_DOMAIN);

        int viewCount = WNCommon.COLLECTION_ALL.equals(collection) ? 3 : 10;
        String[] collections = WNCommon.COLLECTION_ALL.equals(collection) ? WNCollection.COLLECTIONS
                : new String[] { collection };
        logger.info(String.format("[SEARCH::CONTROLLER] PARAM DEBUG MESSAGE => %s,%s,%s,%s", query, collection, startCount, group));

        try {

            CompletableFuture<Map<String, Object>> searchStep = CompletableFuture.supplyAsync(() -> {
                Map<String, Object> resultSearch = null;
                try {
                    resultSearch = innerSearchService.search(query, collections, group, startCount, viewCount);
                } catch(InterruptedException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    return resultSearch;
                }
            });

            CompletableFuture<List<Map<String, String>>> groupStep = CompletableFuture.supplyAsync(() -> {
                List<Map<String, String>> resultGruop = null;
                try {
                    resultGruop = innerSearchService.findGroups(query, collection);
                } catch(InterruptedException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    return resultGruop;
                }
            });

            CompletableFuture<List<String>> weeklyPopKeywordsStep = CompletableFuture.supplyAsync(() -> {
                List<String> resultWeeklyPopKeywords = null;
                try {
                    resultWeeklyPopKeywords = searchService.getPopKeyword("_ALL_", "W");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    return resultWeeklyPopKeywords;
                }
            });

            CompletableFuture<List<String>> dailyPopKeywordsStep = CompletableFuture.supplyAsync(() -> {
                List<String> resultDailyPopKeywords = null;
                try {
                    resultDailyPopKeywords = searchService.getPopKeyword("_ALL_", "D");
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    return resultDailyPopKeywords;
                }
            });

            CompletableFuture<List<FrequentlyAskedMenu>> frequentlyAskedMenusStep = CompletableFuture.supplyAsync(() -> {
                List<FrequentlyAskedMenu> resultFrequentlyAskedMenu = null;
                try {
                    resultFrequentlyAskedMenu = innerSearchService.getFrequentlyAskedMenus();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                } finally {
                    return resultFrequentlyAskedMenu;
                }
            });

            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(searchStep, groupStep, weeklyPopKeywordsStep, dailyPopKeywordsStep, frequentlyAskedMenusStep);
            combinedFuture.get();
            Map<String, Object> result = searchStep.get();
            List<Map<String, String>> groupings = groupStep.get();
            List<String> weeklyPopKeywords = weeklyPopKeywordsStep.get();
            List<String> dailyPopKeywords = dailyPopKeywordsStep.get();
            List<FrequentlyAskedMenu> frequentlyAskedMenus = frequentlyAskedMenusStep.get();
            //Map<String, Object> result = innerSearchService.search(query, collections, group, startCount, viewCount);
            //List<Map<String, String>> groupings = innerSearchService.findGroups(query, collection);
            //List<String> weeklyPopKeywords = searchService.getPopKeyword("_ALL_", "W");
            //List<String> dailyPopKeywords = searchService.getPopKeyword("_ALL_", "D");
            //List<FrequentlyAskedMenu> frequentlyAskedMenus = innerSearchService.getFrequentlyAskedMenus();

            int totalCount = (int) result.get("totalCount");
            String paging = (String) result.get("paging");
            int lastPaging = (int) result.get("lastPaging");
            List<String> realTimeKeywords = (List<String>) result.get("realTimeKeywords");
            Map<String, Integer> collectionCountMap = (Map<String, Integer>) result.get("collectionCountMap");
            Map<String, Object> collectionResultMap = (Map<String, Object>) result.get("collectionResultMap");
            List<GroupResult> collectionGroupResultMap = (List<GroupResult>) result.get("collectionGroupResultMap");

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
            modelAndView.addObject("collectionGroupResultMap", collectionGroupResultMap);

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            return modelAndView;
        }

    }

}
