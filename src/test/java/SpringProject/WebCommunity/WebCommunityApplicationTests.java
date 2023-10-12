package SpringProject.WebCommunity;

import SpringProject.WebCommunity.Domain.Post;
import SpringProject.WebCommunity.Repository.PostRepos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebCommunityApplicationTests {

	@Autowired
	PostRepos postRepos;

	@Test
	void contextLoads() {
		String title = "제목";
		String content = "본문";
		postRepos.save(Post.builder()
				.title(title)
				.contents(content)
				.likes(1)
				.views(1)
				.build());
	}

//	@AfterEach
//	public void clean() {
//		postRepos.deleteAll();
//	}
}
