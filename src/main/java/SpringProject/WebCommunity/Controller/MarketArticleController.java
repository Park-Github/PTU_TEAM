package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Model.Dto.BoardArticleCreateDto;
import SpringProject.WebCommunity.Model.Dto.BoardArticleReadDto;
import SpringProject.WebCommunity.Service.ArticleService;
import SpringProject.WebCommunity.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MarketArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/market/form")
    public String newArticleForm() {return "/form/market-write";}

    // 장터 게시글 Form 데이터 Post mapping
    @PostMapping("/market/articles/create")
    public String createMarketArticle(BoardArticleCreateDto form,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttr) {
        log.info(form.getTitle());
        log.info(form.getContents());
        log.info(form.toString());

        Long boardId = articleService.saveToCreate(form);

        log.info(boardId.toString());

        return "redirect:/market/articles/" + boardId;
    }

    // 장터 게시판 게시글 조회
    @GetMapping("/market/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        Optional<BoardArticleReadDto> articleReadDto = Optional.ofNullable(articleService.findById(id));
        articleReadDto.ifPresent(i -> model.addAttribute("article", i));
        articleReadDto.orElseThrow();
        return "/menu/market";
    }

    // 장터 게시판 게시글 목록 조회
    @GetMapping("/market")
    public String articleListView(Model model) {
        // 모든 Article 가져오기
        List<BoardArticleReadDto> articleList = articleService.findAll();
        // Model에 등록
        model.addAttribute("articleList", articleList);
        return "/menu/market";
    }
}