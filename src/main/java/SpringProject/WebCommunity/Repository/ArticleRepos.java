package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepos extends JpaRepository<Article, Long> {
    
}
