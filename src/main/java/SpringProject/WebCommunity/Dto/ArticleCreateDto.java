package SpringProject.WebCommunity.Dto;

import SpringProject.WebCommunity.Domain.Article;
import lombok.*;

@Getter
@ToString // 데이터를 확인하기 위한 메서드 toString 추가
public class ArticleCreateDto {
    private final String title;
    private final String contents;

    public ArticleCreateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .contents(contents)
                .build();
    }
}
