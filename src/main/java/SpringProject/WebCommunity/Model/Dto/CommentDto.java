package SpringProject.WebCommunity.Model.Dto;

import SpringProject.WebCommunity.Model.Domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    private String contents;
    private String createdBy;
    private LocalDateTime createdTime;
    private LocalDateTime revisedTime;
    private Long articleId;
//    private Long memberId;


    public static CommentDto EntityToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContents(),
                comment.getCreatedBy(),
                comment.getCreatedTime(),
                comment.getRevisedTime(),
                comment.getArticle().getId()
//                comment.getMember().getId()
        );
    }
}
