package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Dto.MemberCreateDto;
import SpringProject.WebCommunity.Exception.RegisterException;
import SpringProject.WebCommunity.Model.Response.Response;
import SpringProject.WebCommunity.Service.MemberService;
import SpringProject.WebCommunity.Service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final ResponseService responseService;

    @PostMapping("/register")
    public Response register(MemberCreateDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        String nickname = dto.getNickname();
        String contact = dto.getContact();
        LocalDate birth = dto.getBirth();

        if (email == null || password == null || nickname == null || contact == null || birth == null) {
            throw new RegisterException(RegisterException.Reason.INCOMPLETE_FORM);
        }

        if (password.length() < 8) {
            throw new RegisterException(RegisterException.Reason.SHORT_PASSWORD);
        }

        memberService.save(dto.toEntity());
        return responseService.getSuccessResponse();
    }

}
