package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ArticleRepos extends JpaRepository<Article, Long> {
    
}
