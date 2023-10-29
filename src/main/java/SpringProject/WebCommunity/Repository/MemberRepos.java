package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepos extends JpaRepository<Member, String> {

}
