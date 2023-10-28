package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class baseTimeEntityTest {
    @Autowired
    ArticleRepos ArticleRepos;
    @Test
    public void reg_BaseTimeEntity(){
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0,0);
        ArticleRepos.save(Article.builder()
                .title("테스트용 제목")
                .nickName("lsfa")
                .contents("테스트용 본문")
                .build());

        List<Article> articleList = ArticleRepos.findAll();

        Article article = articleList.get(0);

        System.out.println(">>>>>>>>>> createDate = " + article.getCreatedTime() + " revisedDated = " + article.getRevisedTime());
    }
}
