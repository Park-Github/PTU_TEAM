package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Domain.Member;
import SpringProject.WebCommunity.Repository.MemberRepos;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepos memberRepos;
    @Autowired
    public MemberService(MemberRepos memberRepos) {
        this.memberRepos = memberRepos;
    }

    public Optional<Member> getMember(HttpSession session) {
        String id = (String) session.getAttribute("member-id");
        return Optional.empty();
    }

    public Long save(Member member) {
        return memberRepos.save(member).getId();
    }

}
