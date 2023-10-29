package SpringProject.WebCommunity.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id @Column(updatable = false, nullable = false)
    private Long id;
    @Column(nullable = false, length = 256)
    private String password;
    @Column(nullable = false, unique = true, length = 64)
    private String nickName;
    @Column(nullable = false, length = 64)
    private String email;
    @Column(length = 15)
    private String contact;
    @Column(length = 128)
    private String birth;
    @Column(nullable = false, updatable = false, unique = true)
    private LocalDateTime createdTime;
    private String profileImg;

    @Builder // setter
    public Member(String password,String nickName, String email, String contact, String birth) {
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.contact = contact;
        this.birth = birth;
    }
}
