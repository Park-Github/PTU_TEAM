package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Model.Dto.CommentDto;
import SpringProject.WebCommunity.Service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentApiController {
    private final CommentService commentService;

    // 댓글 READ
    @GetMapping("/api/articles/{id}/comments")
    public ResponseEntity<List<CommentDto>> readComments(@PathVariable Long id) {
        // Service 계층에서 Comment를 조회
        List<CommentDto> dtoList = commentService.findComments(id);
        log.info(dtoList.toString());
        return ResponseEntity.status(HttpStatus.OK).body(dtoList);
    }
    // 댓글 CREATE
    @PostMapping("/api/articles/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long id,
                                                    @RequestBody CommentDto reqestDto) {
        // Service 계층에서 Comment를 생성
        CommentDto responseDto = commentService.createComment(id, reqestDto);
        return  ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 댓글 UPDATE
    @PatchMapping("api/articles/comments/{id}")
    public  ResponseEntity<CommentDto> updateComment(@PathVariable Long id,
                                                     @RequestBody CommentDto requestDto){
        // Service 계층에서 Comment를 수정
        CommentDto responseDto = commentService.updateComment(id, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 댓글 DELETE
    @DeleteMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long id) {
        // Service 계층에서 Comment 를 삭제
        CommentDto responseDto = commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
