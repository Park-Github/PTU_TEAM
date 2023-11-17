package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.Article;
import SpringProject.WebCommunity.Model.Dto.ArticleReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepos extends JpaRepository<Article, Long> {

    Page<Article> findAllByCategory(String category, Pageable pageable);
    Page<Article> findAllByCategoryAndTitleContaining(String category, String title, Pageable pageable);
    Page<Article> findAllByCategoryAndContentsContaining(String category, String contents, Pageable pageable);
    Page<Article> findAllByCategoryAndCreatedByContaining(String category, String createdBy, Pageable pageable);
    default ArticleReadDto entityToDto(Article article) {
        return new ArticleReadDto(article);
    }
}
