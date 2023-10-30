package SpringProject.WebCommunity.Domain;

import jakarta.persistence.*;
import lombok.*;


@Entity // 게시글 엔티티 클래스
@NoArgsConstructor //기본 생성자 추가
@AllArgsConstructor// 모든 필드 생성자 추가
@Getter
public class BoardArticle extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO) // PK auto_increment 옵션
    private Long id; //id 필드
    @Column(length = 64,  unique = true)
    private String nickName;
    @Column(columnDefinition = "INT default 0")
    int views;
    @Column(columnDefinition = "INT default 0")
    int likes;
    @Column(length = 500, nullable = false)
    String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    String contents;
    @Column
    String category;

    @Builder
    public BoardArticle(String category, String nickName, String title, String contents) {
        this.category = category;
        this.nickName = nickName;
        this.title = title;
        this.contents = contents;
    }

    public void update(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

}
