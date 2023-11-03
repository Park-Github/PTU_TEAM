package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.BoardArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepos extends JpaRepository<BoardArticle, Long> {
    
}
