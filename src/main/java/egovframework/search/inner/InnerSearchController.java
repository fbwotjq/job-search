package egovframework.search.inner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/inner/search")
public class InnerSearchController {

    private final static String COLLECTION_ALL = "ALL";
    private final static String EMPTY_STRING = "";
    private final static String ZERO = "0";

    @Autowired InnerSearchService innerSearchService;

    @RequestMapping(value = "/search.do", method={RequestMethod.GET, RequestMethod.POST})
    public ModelAndView search(
            @RequestParam(value = "query", defaultValue = EMPTY_STRING, required = false) String query,
            @RequestParam(value = "collection", defaultValue = COLLECTION_ALL, required = false) String collection,
            @RequestParam(value = "startCount", defaultValue = ZERO, required = false) int startCount
    ) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search/inner/search");

        return modelAndView;
    }

}
