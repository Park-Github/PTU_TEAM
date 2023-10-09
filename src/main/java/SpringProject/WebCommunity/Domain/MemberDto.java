package SpringProject.WebCommunity.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class MemberDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Long password;
    private String name;
    private String nickName;
    private String email;
    private String contact;
    private String address;
    private LocalDateTime createdTime;
    private String profileImg;



}
