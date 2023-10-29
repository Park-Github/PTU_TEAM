package SpringProject.WebCommunity.Dto;

import SpringProject.WebCommunity.Domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class MemberCreateDto {

    private final String email;

    private final String password;

    private final String nickname;

    private final String contact;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate birth;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickName(nickname)
                .contact(contact)
                .birth(birth.toString())
                .build();
    }

}
