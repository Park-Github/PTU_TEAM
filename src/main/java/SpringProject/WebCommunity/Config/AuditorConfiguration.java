package SpringProject.WebCommunity.Config;

import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Configuration
public class AuditorConfiguration implements AuditorAware<String> {

    private final MemberService memberService;

    public AuditorConfiguration(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        Optional<Member> member = memberService.getMember(request);
        return Optional.ofNullable(member.get().getNickName());
    }
}
