package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static SpringProject.WebCommunity.Model.Domain.QMember.*;

@Repository
@Transactional
public class MemberQueryRepos {
    private final JPAQueryFactory queryFactory;
    public MemberQueryRepos(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Member> findAllByNickName(String nickName) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.nickName.like("%" + nickName + "%"))
                .fetch();
    }

    public Member findOneByEmail(String email) {
        return (Member) queryFactory
                .select(member)
                .from(member)
                .where(member.email.eq(email))
                .fetch();
    }
}
