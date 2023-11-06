package SpringProject.WebCommunity.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LoginException extends ControllerException {

    @Getter
    @RequiredArgsConstructor
    public enum Reason {
        INCOMPLETE_FORM(1, "Incomplete form"),
        CREDENTIAL_ERROR(2, "Credential error"),
        ;

        private final int status;
        private final String message;
    }

    private final Reason reason;

    public LoginException(Reason reason) {
        super(reason.getStatus(), reason.getMessage());
        this.reason = reason;
    }

}
