package SpringProject.WebCommunity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RenderingController {
    // -- headline --
    @GetMapping("/")
    public String welcomeHome() { return "home"; }
    @GetMapping("/register")
    public String registerView() { return "/certification/register"; }
    @GetMapping("/login")
    public String loginView() { return "/certification/login"; }
    @GetMapping("/profile")
    public String profileView() { return "/profile/mypage";}
    @GetMapping("/sitemap")
    public String sitemapView(){
        return "sitemap";
    }
    //

    // -- board --
    @GetMapping("/board-all")
    public String allCategoryView(){
        return "board-all";
    }
    //

    // -- study --
    @GetMapping("/study")
    public String studyView(){
        return "study";
    }
    //

    // market
    @GetMapping("/market")
    public String marketView() { return "/board/market";}
    //

    @GetMapping("/scrap")
    public String scrapView(){
        return "/profile/scrap";
    }

}
