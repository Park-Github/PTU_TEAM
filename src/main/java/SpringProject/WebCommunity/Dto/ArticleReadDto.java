package SpringProject.WebCommunity.Dto;

import SpringProject.WebCommunity.Domain.Article;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleReadDto {
    private final Long id;
    private final String title;
    private final String nickName;
    private final String contents;
    private final int views;
    private final int likes;
    private final LocalDateTime createdTime;
    private final LocalDateTime revisedTime;

    public ArticleReadDto(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.nickName = entity.getNickName();
        this.contents = entity.getContents();
        this.views = entity.getViews();
        this.likes = entity.getLikes();
        this.createdTime = entity.getCreatedTime();
        this.revisedTime = entity.getRevisedTime();
    }
}
