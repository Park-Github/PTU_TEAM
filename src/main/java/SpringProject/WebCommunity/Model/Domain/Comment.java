package SpringProject.WebCommunity.Model.Domain;

import SpringProject.WebCommunity.Model.Dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Comment extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonProperty("articleId")
    private Article article;

    public Comment(String contents, Article article) {
        this.contents = contents;
        this.article = article;
    }

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @Builder
//    public Comment(Long id, String contents, Article article, Member member) {
//        this.id = id;
//        this.contents = contents;
//        this.article = article;
//        this.member = member;
//    }

    public static Comment commentToEntity(CommentDto dto, Article article) {
        // 예외 처리
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글 PK 제약사항 위반!");
        if (!dto.getArticleId().equals(article.getId()))
            throw new IllegalArgumentException("댓글 생성 실패! 게시글 PK 제약사항 위반!");

        // 엔티티 생성 및 반환
        return new Comment(
                dto.getContents(),
                article
        );
    }

    public void patch(CommentDto requestDto) {
        log.info(this.id.toString());
        // 예외처리
        if (!this.id.equals(requestDto.getId()))
            throw new IllegalArgumentException("댓글 수정 실패! 댓글 PK 제약사항 위반!");
        // object 갱신
        if (!requestDto.getContents().isEmpty())
            this.contents = requestDto.getContents();
    }

    @PreUpdate
    public void updateRevisedTime() {
        super.updateRevisedTime();
    }
}
