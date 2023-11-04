package SpringProject.WebCommunity.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ControllerException extends RuntimeException {

    private final int status;
    private final String message;

}
