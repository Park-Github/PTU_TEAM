package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Dto.ArticleReadDto;
import SpringProject.WebCommunity.Model.Dto.ArticleUpdateDto;
import SpringProject.WebCommunity.Service.ArticleService;
import org.springframework.ui.Model;

public class CommonController {

    public static void UpdateAndRegisterModel(String category,
                                              Long id,
                                              Model model,
                                              ArticleService articleService) {
        ArticleReadDto readDto = articleService.findById(id);
        ArticleUpdateDto updateDto = new ArticleUpdateDto(id, readDto.getTitle(), readDto.getContents());

        model.addAttribute("cat", category);
        model.addAttribute("articleDto", updateDto);
        model.addAttribute("id", id);
    }
}
