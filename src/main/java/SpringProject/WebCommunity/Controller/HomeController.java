package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Service.ArticleService;
import SpringProject.WebCommunity.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final MemberService memberService;
    @GetMapping("/")
    public String randeringHome(Model model,
                                HttpServletRequest request) {

//        if (memberService.getMember(request).isEmpty()){ // TODO 현재 페이지 URL 경로 가져와서 뒤로가기 버튼 만들기 front programming 필요
//            String url = request.getHeader("Referer");
//            model.addAttribute("prevURL", URLDecoder.decode(url, StandardCharsets.UTF_8));
//            log.info(url);
//        }
        ArrayList<String> categoryList = new ArrayList<>(List.of("free", "sharing", "qna", "promotion"));
        Map<String, List<Article>> boardMap = new HashMap<>();

        model.addAttribute("BoardMap", initBoardMap(boardMap, categoryList));

        return "/home";
    }

    private Map<String, List<Article>> initBoardMap(
            Map<String, List<Article>> boardMap,
            ArrayList<String> category) {
        category.stream().forEach((element) -> {
            List<Article> list = articleService.find2ByCategoryDesc(element);
            boardMap.put(element, list);
        });
        return boardMap;
    }
}
