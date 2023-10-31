package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Dto.BoardArticleCreateDto;
import SpringProject.WebCommunity.Dto.BoardArticleReadDto;
import SpringProject.WebCommunity.Dto.BoardArticleUpdateDto;
import SpringProject.WebCommunity.Service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardArticleController {

    private final ArticleService articleService;

    // 게시판 글쓰기 페이지 요청 처리
    @GetMapping("/board/form/{cat}")
    public String newArticleForm(@PathVariable(name = "cat") String category,
                                 Model model, BoardArticleCreateDto dto) {
        model.addAttribute("articleDto", dto);
        model.addAttribute("articleCat", category);
        return "/form/post-write";
    }

    // 게시판 게시글 입력 데이터 POST mapping
    @PostMapping("/board/create/{articleCat}")
    public String createBoardArticle(@PathVariable(name = "articleCat")String category, BoardArticleCreateDto form) {
        log.info(form.getTitle());
        log.info(form.getContents());
        log.info(form.toString());
        log.info(category);

        form.setCategory(category);

        Long boardId = articleService.save(form);

        log.info(boardId.toString());

        return "redirect:/board/view/" + boardId;
    }

    // 게시판 게시글 조회
    @GetMapping("/board/view/{id}")
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
        List<BoardArticleReadDto> articleList = articleService.findAll();
        // Model에 등록
        model.addAttribute("boardArticleList", articleList);
        // URL 파라미터 값(category) Model 등록
        model.addAttribute("boardCat", category);
        return "/menu/article-list";
    }

    // 게시판 게시글 수정 페이지 요청 처리
    @GetMapping("/board/edit")
    public String articleEditPageView(@RequestParam(name = "category") String category,
                                      @RequestParam(name = "id") Long id,
                                      Model model) {
        BoardArticleReadDto readDto = articleService.findById(id);
        BoardArticleUpdateDto updateDto = new BoardArticleUpdateDto(readDto.getTitle(), readDto.getContents());
        log.info(String.valueOf(id));
        log.info(updateDto.getTitle());
        log.info(updateDto.getContents());

        model.addAttribute("articleDto", updateDto);
        model.addAttribute("cat", category);

        return "/form/post-edit";
    }
}
