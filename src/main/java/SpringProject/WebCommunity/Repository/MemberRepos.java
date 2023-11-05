package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Model.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepos extends JpaRepository<Member, String> {

    @Query("select m from Member m where m.email = ?1")
    Member findByEmail(String email);

    @Query("select m from Member m where m.nickName = ?1")
    Member findByNickname(String nickname);

}
