package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Dto.ArticleReadDto;
import SpringProject.WebCommunity.Model.Dto.ArticleUpdateDto;
import SpringProject.WebCommunity.Model.Dto.PageRequestDto;
import SpringProject.WebCommunity.Model.Dto.PageResultDto;
import SpringProject.WebCommunity.Service.ArticleService;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    public static void registerPageModels(String category, String condition, String keyWord, String sort,
                                          PageRequestDto pageRequestDto, Model model, ArticleService articleService, Logger logger) {
        logger.info(condition);
        logger.info(category);
        switch (condition) {

            case "title":
                if(category.equals("market-sell") || category.equals("market-buy")) {
                    PageResultDto<ArticleReadDto, Article> resultDto1
                            = articleService.getListByTitle(pageRequestDto, sort, category, keyWord);
                    logger.info(resultDto1.getDtoList().toString());
                    model.addAttribute(category.equals("market-sell") ? "sellList" : "buyList", resultDto1);
                }
                else {
                    PageResultDto<ArticleReadDto, Article> resultDto1
                            = articleService.getListByTitle(pageRequestDto, sort, category, keyWord);
                    logger.info(resultDto1.getDtoList().toString());
                    model.addAttribute("boardArticleList", resultDto1);
                    model.addAttribute("boardCat", category);
                }

                break;
            case "contents":
                if(category.equals("market-sell") || category.equals("market-buy")) {
                    PageResultDto<ArticleReadDto, Article> resultDto2
                            = articleService.getListByContents(pageRequestDto, sort, category, keyWord);
                    model.addAttribute(category.equals("market-sell") ? "sellList" : "buyList", resultDto2);
                }
                else {
                    PageResultDto<ArticleReadDto, Article> resultDto1
                            = articleService.getListByTitle(pageRequestDto, sort, category, keyWord);
                    model.addAttribute("boardArticleList", resultDto1);
                    model.addAttribute("boardCat", category);
                }
                break;
            case "createdBy":
                if(category.equals("market-sell") || category.equals("market-buy")) {
                PageResultDto<ArticleReadDto, Article> resultDto3
                        = articleService.getListByUserName(pageRequestDto, sort, category, keyWord);
                model.addAttribute(category.equals("market-sell") ? "sellList" : "buyList", resultDto3);
                model.addAttribute("boardCat", category);
                }
                else {
                    PageResultDto<ArticleReadDto, Article> resultDto1
                            = articleService.getListByTitle(pageRequestDto, sort, category, keyWord);
                    model.addAttribute("boardArticleList", resultDto1);
                    model.addAttribute("boardCat", category);
                }
                break;
            default:
                PageResultDto<ArticleReadDto, Article> resultDto4
                        = articleService.getList(pageRequestDto, sort, category);
                model.addAttribute("boardArticleList", resultDto4);
                model.addAttribute("boardCat", category);
                break;
        }

        model.addAttribute("sort", sort);

    }

}

