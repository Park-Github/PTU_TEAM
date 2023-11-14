package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static SpringProject.WebCommunity.Model.Domain.QArticle.article;

@Repository
@Transactional
public class ArticleQueryRepos {
    private final JPAQueryFactory queryFactory;
    public ArticleQueryRepos(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Article> find2ByCategoryDesc(String category) {
        return queryFactory
                .select(article)
                .from(article)
                .where(article.category.eq(category))
                .orderBy(article.createdTime.desc())
                .limit(2)
                .fetch();
    }

    public List<Article> find3ByCategoryDesc(String category) {
        return queryFactory
                .select(article)
                .from(article)
                .where(article.category.eq(category))
                .orderBy(article.createdTime.desc())
                .limit(3)
                .fetch();
    }

    public void updateViewCount(Long id) {
        queryFactory
                .update(article)
                .set(article.views, article.views.add(1))
                .where(article.id.eq(id))
                .execute();

    }

    public void updateLikeCount(Long id) {
        queryFactory
                .update(article)
                .set(article.likes, article.likes.add(1))
                .where(article.id.eq(id))
                .execute();

    }

}
