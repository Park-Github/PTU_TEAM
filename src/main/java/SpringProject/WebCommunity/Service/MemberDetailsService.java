package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Domain.Member;
import SpringProject.WebCommunity.Domain.MemberPrincipal;
import SpringProject.WebCommunity.Repository.MemberRepos;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepos memberRepos;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepos.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new MemberPrincipal(member);
    }
}
