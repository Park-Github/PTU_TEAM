package SpringProject.WebCommunity.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleUpdateDto {
    private String title;
    private String contents;
    public ArticleUpdateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
