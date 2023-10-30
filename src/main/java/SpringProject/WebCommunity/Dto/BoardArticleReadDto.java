package SpringProject.WebCommunity.Dto;

import SpringProject.WebCommunity.Domain.BoardArticle;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class BoardArticleReadDto {
    private final Long id;
    private final String title;
    private final String nickName;
    private final String contents;
    private final String category;
    private final int views;
    private final int likes;
    private final LocalDateTime createdTime;
    private final LocalDateTime revisedTime;

    public BoardArticleReadDto(BoardArticle entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.nickName = entity.getNickName();
        this.contents = entity.getContents();
        this.category = entity.getCategory();
        this.views = entity.getViews();
        this.likes = entity.getLikes();
        this.createdTime = entity.getCreatedTime();
        this.revisedTime = entity.getRevisedTime();
    }
}
