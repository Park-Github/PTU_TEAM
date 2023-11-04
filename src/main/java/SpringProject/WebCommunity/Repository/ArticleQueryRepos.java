package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.BoardArticle;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static SpringProject.WebCommunity.Domain.QBoardArticle.*;

@Repository
@Transactional
public class ArticleQueryRepos {
    private final JPAQueryFactory queryFactory;
    public ArticleQueryRepos(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<BoardArticle> findAllByTitle(String title) {
        return queryFactory
                .select(boardArticle)
                .from(boardArticle)
                .where(boardArticle.title.like("%" + title + "%"))
                .fetch();
    }

    public List<BoardArticle> findAllByContents(String contents) {
        return queryFactory
                .select(boardArticle)
                .from(boardArticle)
                .where(boardArticle.contents.like("%" + contents + "%"))
                .fetch();
    }

    public List<BoardArticle> findAllByCategory(String category) {
        return queryFactory
                .select(boardArticle)
                .from(boardArticle)
                .where(boardArticle.category.eq(category))
                .fetch();
    }

    public List<BoardArticle> findAllOrderByTimeDesc(String category) {
        return queryFactory
                .select(boardArticle)
                .from(boardArticle)
                .where(boardArticle.category.eq(category))
                .orderBy(boardArticle.createdTime.desc())
                .fetch();
    }

    public List<BoardArticle> findAllOrderByLikeDesc(String category) {
        return queryFactory
                .select(boardArticle)
                .from(boardArticle)
                .orderBy(boardArticle.likes.desc())
                .fetch();
    }

    public List<BoardArticle> findAllOrderByViewDesc(String category) {
        return queryFactory
                .select(boardArticle)
                .from(boardArticle)
                .orderBy(boardArticle.views.desc())
                .fetch();
    }
}
