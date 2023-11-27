package SpringProject.WebCommunity.Model.Dto;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Domain.Member;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleReadDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final String category;
    private final int views;
    private final int likes;
    private final String createdBy;
    private final LocalDateTime createdTime;
    private final LocalDateTime revisedTime;
    private final Member member;

    public ArticleReadDto(Article entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.category = entity.getCategory();
        this.views = entity.getViews();
        this.likes = entity.getLikes();
        this.createdBy = entity.getCreatedBy();
        this.createdTime = entity.getCreatedTime();
        this.revisedTime = entity.getRevisedTime();
        this.member = entity.getMember();
    }

    public Article toEntity() {
        return Article.builder()
                .id(getId())
                .title(getTitle())
                .contents(getContents())
                .category(getCategory())
                .likes(getLikes())
                .views(getViews())
                .createdBy(getCreatedBy())
                .createdTime(getCreatedTime())
                .revisedTime(getRevisedTime())
                .member(getMember())
                .build();
    }
}
