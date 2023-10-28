package SpringProject.WebCommunity.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@Table(name = "Member")
public class Member {
    @Id @Column(updatable = false, nullable = false, length = 64)
    private String id;
    @Column(nullable = false, length = 256)
    private Long password;
    @Column(nullable = false, length = 32)
    private String name;
    @Column(nullable = false, unique = true, length = 32)
    private String nickName;
    @Column(nullable = false, length = 45)
    private String email;
    @Column(length = 15)
    private String contact;
    @Column(length = 128)
    private String address;
    @Column(nullable = false, updatable = false, unique = true)
    private LocalDateTime createdTime;
    private String profileImg;

    @Builder // setter
    public Member(String email, String contact, String address) {
        this.email = email;
        this.contact = contact;
        this.address = address;
    }
}
