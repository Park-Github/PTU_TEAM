package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.BoardArticle;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static SpringProject.WebCommunity.Model.Domain.QBoardArticle.*;

@Repository
@Transactional
public class ArticleQueryRepos {
    private final JPAQueryFactory queryFactory;
    public ArticleQueryRepos(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<BoardArticle> find2ByCategoryDesc(String category) {
        return queryFactory
                .select(boardArticle)
                .from(boardArticle)
                .where(boardArticle.category.eq(category))
                .orderBy(boardArticle.createdTime.desc())
                .limit(2)
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

    public void updateViewCount(Long id) {
        queryFactory
                .update(boardArticle)
                .set(boardArticle.views, boardArticle.views.add(1))
                .where(boardArticle.id.eq(id))
                .execute();

    }

    public void updateLikeCount(Long id) {
        queryFactory
                .update(boardArticle)
                .set(boardArticle.likes, boardArticle.likes.add(1))
                .where(boardArticle.id.eq(id))
                .execute();

    }

}
