package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Dto.MemberCreateDto;
import SpringProject.WebCommunity.Model.Response.Response;
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

    @PostMapping("/register")
    public Response register(MemberCreateDto dto) {
        return memberService.register(dto);
    }

}
