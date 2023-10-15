package SpringProject.WebCommunity.Controller;

import SpringProject.WebCommunity.Domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelloController {
    @Test
    @GetMapping("/")
    public void saveModel(Model model) throws Exception {
        List<Post> postList = IntStream.rangeClosed(1, 5).asLongStream()
                .mapToObj( i -> {Post p = Post.builder()
                        .title("테스트제목")
                        .likes(2)
                        .views(5)
                        .contents("테스트내용")
                        .build();
                    return p;
                }).collect(Collectors.toList());
    }
}
