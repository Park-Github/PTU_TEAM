package SpringProject.WebCommunity.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardArticleUpdateDto {
    private String title;
    private String contents;
    public BoardArticleUpdateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
