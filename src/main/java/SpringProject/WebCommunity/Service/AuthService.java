package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Domain.Member;
import SpringProject.WebCommunity.Dto.LoginDto;
import SpringProject.WebCommunity.Exception.LoginException;
import SpringProject.WebCommunity.Model.Response.Response;
import SpringProject.WebCommunity.Repository.MemberRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepos memberRepos;

    private final ResponseService responseService;

    private final PasswordService passwordService;

    public Response login(LoginDto dto) {
        if (dto.getEmail() == null || dto.getPassword() == null) {
            throw new LoginException(LoginException.Reason.INCOMPLETE_FORM);
        }

        Member member = memberRepos.findByEmail(dto.getEmail());

        if (member == null) {
            throw new LoginException(LoginException.Reason.CREDENTIAL_ERROR);
        }

        if (passwordService.matchPassword(member, dto.getPassword())) {
            return responseService.getSuccessResponse();
        } else {
            throw new LoginException(LoginException.Reason.CREDENTIAL_ERROR);
        }
    }

}
