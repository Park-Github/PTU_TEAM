package SpringProject.WebCommunity.Domain;

import SpringProject.WebCommunity.Service.SecurityService;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = SecurityService.SALT_LENGTH)
    private String passwordSalt;
    @Column(nullable = false, length = SecurityService.HASH_LENGTH)
    private String passwordHash;
    @Column(nullable = false, unique = true, length = 64)
    private String nickName;
    @Column(nullable = false, unique = true, length = 64)
    private String email;
    @Column(length = 15)
    private String contact;
    @Column(length = 128)
    private String birth;
    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
    private LocalDateTime createdTime;
    private String profileImg;

    @Builder
    public Member(String passwordHash, String passwordSalt, String nickName, String email, String contact, String birth) {
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.nickName = nickName;
        this.email = email;
        this.contact = contact;
        this.birth = birth;
    }

    @PrePersist
    public void onCreate() {
        this.createdTime = LocalDateTime.now();
    }

}
