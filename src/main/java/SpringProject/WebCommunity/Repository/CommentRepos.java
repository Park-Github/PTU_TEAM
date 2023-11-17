package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepos extends JpaRepository<Comment, Long> {
}
