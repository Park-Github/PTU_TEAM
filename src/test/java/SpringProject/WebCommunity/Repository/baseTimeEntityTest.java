package SpringProject.WebCommunity.Repository;

import SpringProject.WebCommunity.Domain.BoardArticle;
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
        ArticleRepos.save(BoardArticle.builder()
                .title("테스트용 제목")
                .nickName("lsfa")
                .contents("테스트용 본문")
                .build());

        List<BoardArticle> boardArticleList = ArticleRepos.findAll();

        BoardArticle boardArticle = boardArticleList.get(0);

        System.out.println(">>>>>>>>>> createDate = " + boardArticle.getCreatedTime() + " revisedDated = " + boardArticle.getRevisedTime());
    }
}
