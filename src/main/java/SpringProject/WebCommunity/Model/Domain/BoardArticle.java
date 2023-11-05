package SpringProject.WebCommunity.Model.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity // 게시글 엔티티 클래스
@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor// 모든 필드 생성자 추가
@Getter
public class BoardArticle extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) // PK auto_increment 옵션
    private Long id; //id 필드
    @Column(length = 64,  unique = true)
    private String nickName;
    @Column(columnDefinition = "INT default 0")
    int views;
    @Column(columnDefinition = "INT default 0")
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
    public BoardArticle(String category, String nickName, String title, String contents, int views, int likes) {
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.nickName = nickName;
        this.views = views;
        this.likes = likes;
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
