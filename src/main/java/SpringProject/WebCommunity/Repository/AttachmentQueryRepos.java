package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.Attachment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static SpringProject.WebCommunity.Model.Domain.QArticle.article;
import static SpringProject.WebCommunity.Model.Domain.QAttachment.attachment;

@Repository
@Transactional
public class AttachmentQueryRepos {
    private final JPAQueryFactory jpaQueryFactory;

    public AttachmentQueryRepos(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public JPAQuery<Attachment> findByArticleId(Long articleId) {
        return jpaQueryFactory
                .select(attachment)
                .from(article)
                .join(attachment).on(attachment.article.id.eq(articleId))
                .where(article.id.eq(articleId));
    }

}