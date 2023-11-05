package SpringProject.WebCommunity.Model.Dto;

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

}
