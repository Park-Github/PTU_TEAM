package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
import SpringProject.WebCommunity.Model.Dto.BoardArticleReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepos extends JpaRepository<BoardArticle, Long> {

    Page<BoardArticle> findAllByCategory(String category, Pageable pageable);
    Page<BoardArticle> findAllByCategoryAndTitleContaining(String category, String title, Pageable pageable);
    Page<BoardArticle> findAllByCategoryAndContentsContaining(String category, String contents, Pageable pageable);
    Page<BoardArticle> findAllByCategoryAndCreatedByContaining(String category, String createdBy, Pageable pageable);
    default BoardArticleReadDto entityToDto(BoardArticle boardArticle) {
        return new BoardArticleReadDto(boardArticle);
    }
}
