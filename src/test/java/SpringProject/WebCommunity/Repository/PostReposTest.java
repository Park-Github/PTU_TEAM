package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PostReposTest {
    @Autowired
    PostRepos PostRepos;
    @Test
    public void reg_BaseTimeEntity(){
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0,0);
        PostRepos.save(Post.builder()
                .title("테스트용 제목")
                .likes(2)
                .views(110)
                .contents("테스트용 본문")
                .build());

        List<Post> postList = PostRepos.findAll();

        Post post = postList.get(0);

        System.out.println(">>>>>>>>>> createDate = " + post.getCreatedTime() + " revisedDated = " + post.getRevisedTime());
    }
}
