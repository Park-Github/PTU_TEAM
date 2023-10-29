package SpringProject.WebCommunity.Model.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingleResponse<T> extends Response {

    // 응답 JSON 에 포함될 단일 데이터
    private T data;

}
