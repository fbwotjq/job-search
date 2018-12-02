package egovframework.search.inner;

import egovframework.search.common.WNCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/inner/search")
public class InnerSearchController {

    @Autowired InnerSearchService innerSearchService;

    @RequestMapping(value = "/search.do", method={RequestMethod.GET, RequestMethod.POST})
    public ModelAndView search(
            @RequestParam(value = "query", defaultValue = WNCommon.EMPTY_STRING, required = false) String query,
            @RequestParam(value = "collection", defaultValue = WNCommon.COLLECTION_ALL, required = false) String collection,
            @RequestParam(value = "startCount", defaultValue = WNCommon.ZERO, required = false) int startCount
    ) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search/inner/search");

        return modelAndView;
    }

}
