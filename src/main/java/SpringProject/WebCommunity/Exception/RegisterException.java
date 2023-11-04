package SpringProject.WebCommunity.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class RegisterException extends ControllerException {

    @Getter
    @RequiredArgsConstructor
    public enum Reason {
        INCOMPLETE_FORM(1, "Incomplete form"),
        SHORT_PASSWORD(2, "Password too short"),
        DUPLICATE_EMAIL(3, "Email already in use"),
        DUPLICATE_NAME(4, "Name already in use"),
        ;

        private final int status;
        private final String message;
    }

    private final Reason reason;

    public RegisterException(Reason reason) {
        super(reason.getStatus(), reason.getMessage());
        this.reason = reason;
    }

}
