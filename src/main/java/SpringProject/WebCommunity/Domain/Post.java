package SpringProject.WebCommunity.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity // 게시글 엔티티 클래스
@NoArgsConstructor //기본 생성자 추가
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // PK auto_increment 옵션
    private Long id; //id 필드
    @Column
    private String writerId;
    @Column int views;
    @Column int likes;
    @Column(length = 400, nullable = false) String title;
    @Column(length = 2000, nullable = false) String contents;
    @Column Timestamp createTime;

    @Builder // setter 역할
    public Post(String title, String contents, int views, int likes) {
        this.title = title;
        this.contents = contents;
        this.views = views;
        this.likes = likes;
    }
}