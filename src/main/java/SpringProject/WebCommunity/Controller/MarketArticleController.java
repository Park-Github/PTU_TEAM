package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Model.Dto.*;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static SpringProject.WebCommunity.Controller.CommonController.UpdateAndRegisterModel;

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

    // 장터 게시글 Form 데이터 Post Request 처리
    @PostMapping("/market/create")
    public String createMarketArticle(ArticleCreateDto form,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttr) {

        Optional<Member> member = memberService.getMember(request);
        if (member.isPresent()) {
            String articleUri = "";
            Member user = member.get();
            ArticleCreateDto dto = new ArticleCreateDto(form.getTitle(), form.getContents(), form.getCategory(), user);
            Long boardId = articleService.saveToCreate(dto);
            redirectAttr.addFlashAttribute("success", "게시글이 등록되었습니다.");
            if(dto.getCategory().equals("market-buy")){
                articleUri = "redirect:/market/buy/articles/" + boardId;
            }

            else if (dto.getCategory().equals("market-sell")) {
                articleUri = "redirect:/market/sell/articles/" + boardId;
            }

            return articleUri;
        }
        else
            return "redirect:/home";

    }

    // 장터 게시판 - 게시글 조회 Request 처리
    @GetMapping(value = {"/market/buy/articles/{id}", "/market/sell/articles/{id}"})
    public String showArticle(@PathVariable Long id,
                              Model model) {
        Optional<ArticleReadDto> articleReadDto = Optional.ofNullable(articleService.findById(id));
        articleReadDto.ifPresent(i -> model.addAttribute("boardArticle", i));
        articleReadDto.orElseThrow();
        return "/menu/article";
    }

    // 장터 게시판 - 게시글 목록 조회 Request 처리
    @GetMapping("/market")
    public String articleListView(@RequestParam(name = "sort", defaultValue = "createdTime") String condition,
                                  PageRequestDto pageRequestDto1,
                                  PageRequestDto pageRequestDto2,
                                  Model model) {

        PageResultDto<ArticleReadDto, Article> pageResultDto1
                = articleService.getList(pageRequestDto1, condition, "market-sell");
        PageResultDto<ArticleReadDto, Article> pageResultDto2
                = articleService.getList(pageRequestDto2, condition, "market-buy");

        // Model에 등록

        model.addAttribute("sellList", pageResultDto1);
        model.addAttribute("buyList", pageResultDto2);
        model.addAttribute("sort", condition);
        return "/menu/market";
    }

    // 장터 게시글 수정 페이지 Request 처리
    @GetMapping("/market/edit") // TODO: 2023-11-03 findById nullPointException 예외처리
    public String articleEditPageView(@RequestParam(name = "category") String category,
                                      @RequestParam(name = "id") Long id,
                                      Model model) {
        UpdateAndRegisterModel(category, id, model, articleService);
        return "/form/market-edit";
    }

    // 장터 게시글 UPDATE 및 게시글 Redirect
    @PostMapping ("/market/edit")
    public String editBoardArticle(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "category") String category,
                                   ArticleUpdateDto form,
                                   Model model) {
        ArticleReadDto article = articleService.updateTitleAndContents(id, form);
        model.addAttribute("boardArticle", article);

        String redirect = "";
        log.info(category);
        if (category.equals("market-sell"))
            redirect += "redirect:/market/sell/articles/" + id;
        else if (category.equals("market-buy"))
            redirect += "redirect:/market/buy/articles/" + id;

        return redirect;
    }

    // 게시판 게시글 삭제 Request 처리
    @GetMapping("/market/delete") // TODO: 2023-11-02 게시글 삭제 요청시 삭제글 없을 시 예외 처리
    public String articleDeletePageView(@RequestParam(name = "id") Long id,
                                        RedirectAttributes redirectAttr) {
        Optional<ArticleReadDto> dto = articleService.optionalFindById(id);

        if (dto.isPresent()) {
            articleService.delete(id);
            redirectAttr.addFlashAttribute("success", "게시글이 삭제되었습니다.");
            return "redirect:/market?sort=";
        }
        else {
            redirectAttr.addFlashAttribute("fail", "게시글을 찾을 수 없어 삭제에 실패하였습니다..");
            return "redirect:/market?sort=";
        }

    }

    @PostMapping("/market/search")
    public String articleSearch(@RequestParam(name = "condition-cat") String conditionCategory,
                                @RequestParam(name = "condition-search", defaultValue = "") String conditionSearch,
                                @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
                                @RequestParam(name = "sort") String sort,
                                PageRequestDto pageRequestDto1,
                                PageRequestDto pageRequestDto2,
                                Model model) {
        if (conditionCategory.equals("market-buy")){
            CommonController.registerPageModels(conditionCategory, conditionSearch,
                    keyWord, sort, pageRequestDto1, model, articleService, log);
            PageResultDto<ArticleReadDto, Article> sellList
                    = articleService.getList(pageRequestDto2, sort, "market-sell");
            model.addAttribute("sellList", sellList);

        } else if (conditionCategory.equals("market-sell")) {
            CommonController.registerPageModels(conditionCategory, conditionSearch,
                    keyWord, sort, pageRequestDto1, model, articleService, log);
            PageResultDto<ArticleReadDto, Article> buyList
                    = articleService.getList(pageRequestDto2, sort, "market-buy");
            model.addAttribute("buyList", buyList);
        }

        return "menu/market";
    }
}

