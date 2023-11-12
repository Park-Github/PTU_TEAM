package SpringProject.WebCommunity.Model.Dto;

import SpringProject.WebCommunity.Model.Domain.Article;
import lombok.Getter;

@Getter
public class ArticleUpdateDto {
    private final Long id;
    private final String title;
    private final String contents;


    public ArticleUpdateDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return Article.builder()
                .title(getTitle())
                .contents(getContents())
                .build();
    }
}
