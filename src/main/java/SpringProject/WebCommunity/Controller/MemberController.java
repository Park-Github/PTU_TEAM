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
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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
