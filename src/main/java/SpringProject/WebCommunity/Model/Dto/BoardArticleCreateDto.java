package SpringProject.WebCommunity.Model.Dto;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
import lombok.*;

@Getter
@ToString // 데이터를 확인하기 위한 메서드 toString 추가
public class BoardArticleCreateDto {
    private final String title;
    private final String contents;
    private final String category;

    public BoardArticleCreateDto(String title, String contents, String category) {
        this.title = title;
        this.contents = contents;
        this.category = category;
    }

    public BoardArticle toEntity() {
        return BoardArticle.builder()
                .title(getTitle())
                .contents(getContents())
                .category(getCategory())
                .build();
    }
}
