//package SpringProject.WebCommunity.Repository;
//
//import SpringProject.WebCommunity.Domain.BoardArticle;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static SpringProject.WebCommunity.Domain.QBoardArticle.*;
//
//@RequiredArgsConstructor
//@Repository
//public class ArticleQueryRepos {
//    private final JPAQueryFactory queryFactory;
//    public List<BoardArticle> findByTitle(String title) {
//        return queryFactory
//                .select(boardArticle)
//                .from(boardArticle)
//                .where(boardArticle.title.eq(title))
//                .fetch();
//    }
//}
