package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Model.Response.ListResponse;
import SpringProject.WebCommunity.Model.Response.Response;
import SpringProject.WebCommunity.Model.Response.SingleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    public Response getSuccessResponse() {
        Response response = new Response();
        markSuccess(response);
        return response;
    }

    public Response getFailResponse() {
        Response response = new Response();
        markFail(response);
        return response;
    }

    public Response getFailResponse(int status, String message) {
        Response response = getFailResponse();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

    public <T> SingleResponse<T> getSingleResponse(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setData(data);
        markSuccess(response);
        return response;
    }

    public <T> ListResponse<T> getListResponse(List<T> list) {
        ListResponse<T> response = new ListResponse<>();
        response.setList(list);
        markSuccess(response);
        return response;
    }

    private <T extends Response> void markSuccess(T response) {
        response.setSuccess(true);
        response.setStatus(0);
        response.setMessage("Success.");
    }

    private <T extends Response> void markFail(T response) {
        response.setSuccess(false);
        response.setStatus(1);
        response.setMessage("Fail.");
    }

}
