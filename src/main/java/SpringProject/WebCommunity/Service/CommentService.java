package SpringProject.WebCommunity.Service;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Domain.Comment;
import SpringProject.WebCommunity.Model.Domain.Member;
import SpringProject.WebCommunity.Model.Dto.CommentDto;
import SpringProject.WebCommunity.Repository.ArticleQueryRepos;
import SpringProject.WebCommunity.Repository.ArticleRepos;
import SpringProject.WebCommunity.Repository.CommentRepos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepos articleRepos;
    private final ArticleQueryRepos articleQueryRepos;
    private final CommentRepos commentRepos;

    public List<CommentDto> findComments(Long id) {
        List<Comment> commentList = articleQueryRepos.findAllComments(id);
        log.info(commentList.toString());
        List<CommentDto> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentDto dto = CommentDto.EntityToDto(comment);
            dtoList.add(dto);
        }
       return dtoList;
    }

    @Transactional
    public CommentDto createComment(Long id, CommentDto dto) {
        Article article = articleRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 댓글이 없음"));
//        Member member = articleQueryRepos.findMember(memberId);
//        log.info(member.toString());
        Comment entity = Comment.commentToEntity(dto, article);
        log.info(entity.toString());
        commentRepos.save(entity);

        return CommentDto.EntityToDto(entity);
    }

    @Transactional
    public CommentDto updateComment(Long id, CommentDto requestDto) {
        Comment comment = commentRepos.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없음"));
        comment.patch(requestDto);
        Comment responseDto = commentRepos.save(comment);
        return CommentDto.EntityToDto(responseDto);
    }

    @Transactional
    public CommentDto deleteComment(Long id) {
        Comment comment = commentRepos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상 댓글이 없음"));
        commentRepos.delete(comment);
        return CommentDto.EntityToDto(comment);
    }
}
