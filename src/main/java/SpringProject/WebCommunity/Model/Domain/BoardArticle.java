package SpringProject.WebCommunity.Model.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@Entity // 게시글 엔티티 클래스
@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor// 모든 필드 생성자 추가
@Getter
public class BoardArticle extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) // PK auto_increment 옵션
    private Long id; //id 필드
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

    @Builder
    public BoardArticle(Long id, String category, String title, String contents, int views, int likes,
                        String createdBy, LocalDateTime createdTime, LocalDateTime revisedTime) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.views = views;
        this.likes = likes;
        super.createdBy = createdBy;
        super.createdTime = createdTime;
        super.revisedTime = revisedTime;
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
