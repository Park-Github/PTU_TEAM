package SpringProject.WebCommunity.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleRepoTest {

    @Autowired
    ArticleRepos articleRepos;

//    @Test
//    public void testInsertDummies(){
//        IntStream.rangeClosed(1, 100).forEach(i ->
//        { Article article = Article.builder()
//                .title("" + i).nickName("fadaff" + i).contents("fasufhisf")
//                .build();
//            articleRepos.save(article);
//        });
//    }
//
//    @Test
//    public void testSelect() {
//        Long id = 100L;
//        Optional<Article> article = articleRepos.findById(id);
//        System.out.println("==============================================");
//        if (article.isPresent()) {
//           Article article1 = article.get();
//            System.out.println(article1);
//        }
//    }
//
//    @Transactional
//    @Test
//    public void testUpdate() {
//        Article article = articleRepos.findById(1L)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
//        article.update("test Title", "test contents");
//        System.out.println(articleRepos.save(article));
//    }
//
//
//    @Test
//    public void testDelete() {
//        IntStream.rangeClosed(1, 100).forEach(i -> {
//            Article article = articleRepos.findById((long) i)
//                    .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
//            articleRepos.delete(article);
//        });
//    }

}
