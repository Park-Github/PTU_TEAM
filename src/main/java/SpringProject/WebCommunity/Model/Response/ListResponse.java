package SpringProject.WebCommunity.Model.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResponse<T> extends Response {

    // 응답 JSON 에 포함될 리스트 데이터
    private List<T> list;

}
