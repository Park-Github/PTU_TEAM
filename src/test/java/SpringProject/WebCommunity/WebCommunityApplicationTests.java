package SpringProject.WebCommunity;

import SpringProject.WebCommunity.Domain.Article;
import SpringProject.WebCommunity.Repository.ArticleRepos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebCommunityApplicationTests {

	@Autowired
	ArticleRepos articleRepos;

	@Test
	void contextLoads() {
		String title = "제목";
		String content = "본문";
		articleRepos.save(Article.builder()
				.title(title)
				.contents(content)
				.build());
	}
}
