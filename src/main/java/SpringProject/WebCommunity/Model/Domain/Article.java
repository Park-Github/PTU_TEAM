package SpringProject.WebCommunity.Model.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Article extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    int views;

    @Column
    int likes;

    @Column(length = 500, nullable = false)
    @NotBlank(message = "제목이 입력되지 않았습니다. 제목을 입력하세요.")
    String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "내용이 입력되지 않았습니다. 내용을 입력하세요.")
    String contents;

    @Column @NotBlank(message = "카테고리 데이터가 입력되지 않았습니다.")
    String category;

    @ManyToOne @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Article(Long id, String category, String title, String contents, int views, int likes,
                   String createdBy, LocalDateTime createdTime, LocalDateTime revisedTime, Member member) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.views = views;
        this.likes = likes;
        super.createdBy = createdBy;
        super.createdTime = createdTime;
        super.revisedTime = revisedTime;
        this.member = member;

    }

    public void update(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

    @PreUpdate
    public void updateRevisedTime() {
        super.updateRevisedTime();
    }

}
