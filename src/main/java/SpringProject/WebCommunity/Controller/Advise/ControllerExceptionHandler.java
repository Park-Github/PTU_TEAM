package SpringProject.WebCommunity.Controller.Advise;

import SpringProject.WebCommunity.Exception.ControllerException;
import SpringProject.WebCommunity.Model.Response.Response;
import SpringProject.WebCommunity.Service.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Response handleException(HttpServletRequest request, Exception e) {
        return responseService.getFailResponse();
    }

    @ExceptionHandler(ControllerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Response handleControllerException(HttpServletRequest request, ControllerException e) {
        return responseService.getFailResponse(e.getStatus(), e.getMessage());
    }

}
