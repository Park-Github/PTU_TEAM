package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Model.Dto.ArticleCreateDto;
import SpringProject.WebCommunity.Model.Dto.ArticleReadDto;
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
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MarketArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/market/form")
    public String newArticleForm(ArticleCreateDto dto,
                                 Model model) {
        model.addAttribute("boardArticle", dto);
        return "/form/market-write";
    }

    // 장터 게시글 Form 데이터 Post mapping
    @PostMapping("/market/create")
    public String createMarketArticle(ArticleCreateDto form,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttr) {

        Optional<Member> member = memberService.getMember(request);
        if (member.isPresent()) {
            String articleUri = "";
            Long boardId = articleService.saveToCreate(form);
            redirectAttr.addFlashAttribute("success", "게시글이 등록되었습니다.");
            if(form.getCategory().equals("market-buy")){
                articleUri = "redirect:/market/buy/articles/" + boardId;
                log.info(articleUri);
            }

            else if (form.getCategory().equals("market-sell")) {
                articleUri = "redirect:/market/sell/articles/" + boardId;
            }

            return articleUri;
        }
        else
            return "redirect:/home";

    }

    // 장터 게시판 게시글 조회
    @GetMapping(value = {"/market/buy/articles/{id}", "/market/sell/articles/{id}"})
    public String showArticle(@PathVariable Long id,
                              Model model) {
        log.info("id = " + id);
        Optional<ArticleReadDto> articleReadDto = Optional.ofNullable(articleService.findById(id));
        articleReadDto.ifPresent(i -> model.addAttribute("boardArticle", i));
        articleReadDto.orElseThrow();
        return "/menu/article";
    }

    // 장터 게시판 게시글 목록 조회
    @GetMapping("/market")
    public String articleListView(Model model) {
        // 모든 Article 가져오기
        List<ArticleReadDto> articleList = articleService.findAll();
        // Model에 등록
        model.addAttribute("articleList", articleList);
        return "/menu/market";
    }
}