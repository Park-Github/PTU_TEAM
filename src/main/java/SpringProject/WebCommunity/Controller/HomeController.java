package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
import SpringProject.WebCommunity.Service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Slf4j
@Controller
@AllArgsConstructor
public class HomeController {

    private final ArticleService articleService;
    @GetMapping("/")
    public String randeringHome(Model model) {

        ArrayList<String> categoryList = new ArrayList<>(List.of("free", "sharing", "qna", "promotion"));
        List<BoardArticle> list = articleService.find2ByCategoryDesc("free");
        Map<String, List<BoardArticle>> boardMap = new HashMap<>();

        model.addAttribute("BoardMap", initBoardMap(boardMap, categoryList));

        return "/home";
    }

    private Map<String, List<BoardArticle>> initBoardMap(
            Map<String, List<BoardArticle>> boardMap,
            ArrayList<String> category) {
        category.stream().forEach((element) -> {
            List<BoardArticle> list = articleService.find2ByCategoryDesc(element);
            boardMap.put(element, list);
        });
        return boardMap;
    }
}
