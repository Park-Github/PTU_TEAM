package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepos extends JpaRepository<Post, Long> {
    
}
