package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Domain.Member;
import SpringProject.WebCommunity.Dto.MemberCreateDto;
import SpringProject.WebCommunity.Exception.RegisterException;
import SpringProject.WebCommunity.Model.Response.Response;
import SpringProject.WebCommunity.Repository.MemberRepos;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepos memberRepos;
    private final PasswordService passwordService;
    private final ResponseService responseService;

    public Optional<Member> getMember(HttpSession session) {
        String id = (String) session.getAttribute("member-id");
        return Optional.empty();
    }

    public Long save(Member member) {
        return memberRepos.save(member).getId();
    }

    public Response register(MemberCreateDto dto) throws RegisterException {
        String email = dto.getEmail();
        String password = dto.getPassword();
        String nickname = dto.getNickname();
        String contact = dto.getContact();
        LocalDate birth = dto.getBirth();

        if (email == null || password == null || nickname == null || contact == null || birth == null) {
            throw new RegisterException(RegisterException.Reason.INCOMPLETE_FORM);
        }

        if (memberRepos.findByEmail(email) != null) {
            throw new RegisterException(RegisterException.Reason.DUPLICATE_EMAIL);
        }

        if (memberRepos.findByNickname(nickname) != null) {
            throw new RegisterException(RegisterException.Reason.DUPLICATE_NAME);
        }

        if (password.length() < 8) {
            throw new RegisterException(RegisterException.Reason.SHORT_PASSWORD);
        }

        String salt = passwordService.getRandomSalt();
        String hash = passwordService.encodePassword(password, salt);
        Member entity = Member.builder()
                .passwordHash(hash)
                .passwordSalt(salt)
                .nickName(nickname)
                .email(email)
                .contact(contact)
                .birth(birth.toString())
                .build();

        save(entity);
        return responseService.getSuccessResponse();
    }
}
