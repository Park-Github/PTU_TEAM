package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Domain.BoardArticle;
import SpringProject.WebCommunity.Dto.BoardArticleCreateDto;
import SpringProject.WebCommunity.Dto.BoardArticleReadDto;
import SpringProject.WebCommunity.Dto.BoardArticleUpdateDto;
import SpringProject.WebCommunity.Service.ArticleService;
import SpringProject.WebCommunity.Service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardArticleController {

    private final ArticleService articleService;
    private final MemberDetailsService memberDetailsService;

    // 게시판 글쓰기 페이지 요청 처리
    @GetMapping("/board/form/{cat}")
    public String newArticleForm(@PathVariable(name = "cat") String category,
                                 Model model, BoardArticleCreateDto dto) {
        model.addAttribute("boardArticle", dto);
        model.addAttribute("category", category);
        return "/form/post-write";
    }

    // 게시판 게시글 입력 데이터 POST mapping
    @PostMapping("/board/create")
    public String createBoardArticle(@RequestParam(name = "category")String category,
                                     BoardArticleCreateDto form,
                                     RedirectAttributes redirectAttr) {
        log.info(form.getTitle());
        log.info(form.getContents());
        log.info(form.toString());
        log.info(category);

        Long boardId = articleService.saveToCreate(form);
        redirectAttr.addFlashAttribute("success", "게시글이 등록되었습니다.");
        log.info(boardId.toString());

        return "redirect:/board/view/" + boardId;
    }

    // 게시판 게시글 조회
    @GetMapping("/board/view/{id}") // TODO: 2023-11-03 articleReadDto null 예외처리
    public String showArticle(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        Optional<BoardArticleReadDto> articleReadDto = Optional.ofNullable(articleService.findById(id));
        articleReadDto.ifPresent(i -> model.addAttribute("boardArticle", i));
        articleReadDto.orElseThrow();
        return "/menu/article";
    }

    // 게시판 게시글 목록 조회
    @GetMapping("/board/view")
    public String articleListView(@RequestParam(name = "category") String category, Model model) {
        // 모든 Article 가져오기
        List<BoardArticle> articleList = articleService.findAllByTimeDesc(category);
        // Model에 등록
        model.addAttribute("boardArticleList", articleList);
        // URL 파라미터 값(category) Model 등록
        model.addAttribute("boardCat", category);
        return "/menu/article-list";
    }

    // 게시판 게시글 수정 페이지 요청 처리
    @GetMapping("/board/edit") // TODO: 2023-11-03 findById nullPointException 예외처리
    public String articleEditPageView(@RequestParam(name = "category") String category,
                                      @RequestParam(name = "id") Long id,
                                      Model model) {
        BoardArticleReadDto readDto = articleService.findById(id);
        BoardArticleUpdateDto updateDto = new BoardArticleUpdateDto(id, readDto.getTitle(), readDto.getContents());
        log.info(String.valueOf(id));
        log.info(updateDto.getTitle());
        log.info(updateDto.getContents());

        model.addAttribute("cat", category);
        model.addAttribute("articleDto", updateDto);
        model.addAttribute("id", id);

        return "/form/post-edit";
    }

    // 게시판 게시글 수정 데이터 POST mapping
    @PostMapping ("/board/edit")
    public String editBoardArticle(@RequestParam(name = "id") Long id,
                                   BoardArticleUpdateDto form,
                                   Model model) {
        log.info(form.getTitle() + "제목");
        log.info(form.getContents() + "내용");
        log.info(id.toString() + "id");

        BoardArticleReadDto article = articleService.update(id, form);

        log.info(article.getCategory() + "카테고리");

        model.addAttribute("boardArticle", article);

        return "redirect:/board/view/" + id;
    }

    // 게시판 게시글 삭제 요청
    @GetMapping("/board/delete/{category}") // TODO: 2023-11-02 게시글 삭제 요청시 삭제글 없을 시 예외 처리
    public String articleDeletePageView(@RequestParam(name = "id") Long id,
                                        @PathVariable(name = "category") String category,
                                        RedirectAttributes redirectAttr) {
        Optional<BoardArticleReadDto> dto = articleService.optionalFindById(id);

        if (dto.isPresent()) {
            articleService.delete(id);
            redirectAttr.addFlashAttribute("success", "게시글이 삭제되었습니다.");
            return switch (category) {
                default -> "redirect:/board/view?category=free";
                case "sharing" -> "redirect:/board/view?category=sharing";
                case "qna" -> "redirect:/board/view?category=qna";
                case "promotion" -> "redirect:/board/view?category=promotion";
            };
        }
        else {
            redirectAttr.addFlashAttribute("fail", "게시글을 찾을 수 없어 삭제에 실패하였습니다..");
            return switch (category) {
                default -> "redirect:/board/view?category=free";
                case "sharing" -> "redirect:/board/view?category=sharing";
                case "qna" -> "redirect:/board/view?category=qna";
                case "promotion" -> "redirect:/board/view?category=promotion";
            };
        }
    }
}

