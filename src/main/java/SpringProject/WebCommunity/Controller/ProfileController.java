package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final MemberService memberService;

    @GetMapping("/profile")
    public String profilePageRequest(HttpServletRequest request,
                                     Model model) {
        Optional<Member> member = memberService.getMember(request);
        member.ifPresent(value -> {
            model.addAttribute("member", value);
        });
        return "profile/profile-page";
    }
}
