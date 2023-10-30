package SpringProject.WebCommunity.Dto;

import SpringProject.WebCommunity.Domain.BoardArticle;
import lombok.*;

@Getter
@ToString // 데이터를 확인하기 위한 메서드 toString 추가
public class BoardArticleCreateDto {
    private final String title;
    private final String contents;
    private String Category;

    public BoardArticleCreateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public BoardArticle toEntity() {
        return BoardArticle.builder()
                .title(getTitle())
                .contents(getContents())
                .category(getCategory())
                .build();
    }
}
