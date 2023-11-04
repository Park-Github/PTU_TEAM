package SpringProject.WebCommunity.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 64)
    private String nickName;
    @Column(nullable = false, unique = true, length = 64)
    private String email;
    @Column(length = 15)
    private String contact;
    @Column(length = 128)
    private String birth;
    @Column(nullable = false, columnDefinition = "varchar(32) default 'user'")
    private String role;
    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
    private LocalDateTime createdTime;
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String password;
    @Column(nullable = false)
    private boolean credentialsExpired;
    private String profileImg;

    @Builder
    public Member(String nickName, String email, String password, boolean credentialsExpired, String contact, String birth, String role) {
        this.password = password;
        this.credentialsExpired = credentialsExpired;
        this.nickName = nickName;
        this.email = email;
        this.contact = contact;
        this.birth = birth;
        this.role = role;
    }

    @PrePersist
    public void onCreate() {
        this.createdTime = LocalDateTime.now();
    }

}
