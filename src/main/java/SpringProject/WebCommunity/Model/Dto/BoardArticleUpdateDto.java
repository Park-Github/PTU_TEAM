package SpringProject.WebCommunity.Model.Dto;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
import lombok.Getter;

@Getter
public class BoardArticleUpdateDto {
    private final Long id;
    private final String title;
    private final String contents;

    public BoardArticleUpdateDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public BoardArticle toEntity() {
        return BoardArticle.builder()
                .title(getTitle())
                .contents(getContents())
                .build();
    }
}
