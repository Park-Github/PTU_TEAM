package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Dto.ArticleCreateDto;
import SpringProject.WebCommunity.Dto.ArticleReadDto;
import SpringProject.WebCommunity.Service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/article-list/articles")
    public String articleListView() {
        return "/menu/article";
    }

    @GetMapping("/article-list/form")
    public String newArticleForm() {
        return "/form/post-write";
    }

    @PostMapping("/article-list/create")
    public String createArticle(ArticleCreateDto form) {
        log.info(form.getTitle());
        log.info(form.getContents());
        log.info(form.toString());

        Long boardId = articleService.save(form);

        log.info(boardId.toString());

        return "redirect:/article-list/articles/" + boardId;
    }

    @GetMapping("/article-list/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        Optional<ArticleReadDto> articleReadDto = Optional.ofNullable(articleService.findById(id));
        articleReadDto.ifPresent(i -> model.addAttribute("article", i));


        return "redirect:/";
    }

}
