package SpringProject.WebCommunity.Model.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {

    // 요청에 대한 응답의 성공 여부
    private boolean success;

    // 응답 상태 코드
    // 0 = 성공, 1+ : 실패
    private int status;

    // 응답 메시지
    private String message;

}
