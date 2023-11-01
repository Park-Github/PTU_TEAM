package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Dto.LoginDto;
import SpringProject.WebCommunity.Dto.MemberCreateDto;
import SpringProject.WebCommunity.Model.Response.Response;
import SpringProject.WebCommunity.Service.AuthService;
import SpringProject.WebCommunity.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping("/register")
    public Response register(MemberCreateDto dto) {
        return memberService.register(dto);
    }

    @PostMapping("/login")
    public Response login(LoginDto dto) {
        return authService.login(dto);
    }

}
