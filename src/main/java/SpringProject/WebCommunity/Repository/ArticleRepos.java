package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.BoardArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepos extends JpaRepository<BoardArticle, Long> {
    
}
