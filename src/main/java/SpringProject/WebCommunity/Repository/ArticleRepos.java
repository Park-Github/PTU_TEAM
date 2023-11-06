package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
import SpringProject.WebCommunity.Model.Dto.BoardArticleReadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepos extends JpaRepository<BoardArticle, Long> {
    default BoardArticleReadDto entityToDto(BoardArticle boardArticle) {
        return new BoardArticleReadDto(boardArticle);
    }
}
