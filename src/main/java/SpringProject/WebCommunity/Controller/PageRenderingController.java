package SpringProject.WebCommunity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageRenderingController {
    // -- menu --
    @GetMapping("/")
    public String welcomeHome() { return "home"; }
    @GetMapping("/register")
    public String registerView() { return "/certification/register"; }
    @GetMapping("/login")
    public String loginView() { return "/certification/login"; }
    @GetMapping("/profile")
    public String profileView() { return "/profile/profile-page";}
    @GetMapping("/sitemap")
    public String sitemapView(){
        return "/menu/sitemap";
    }
    @GetMapping("/job")
    public String jobView(){
        return "/menu/job";
    }
    @GetMapping("/study")
    public String studyView(){
        return "/menu/study";
    }
    @GetMapping("/search")
    public String searchView(){
        return "/menu/search";
    }

    // profile
    @GetMapping("/profile/comments")
    public String myCommentsView(){
        return "/profile/comments";
    }
    @GetMapping("/profile/articles")
    public String myArticlesView(){
        return "/profile/articles";
    }
    @GetMapping("/profile/scraps")
    public String myScrapView(){
        return "/profile/scraps";
    }

    // find id & pw
    @GetMapping("/find-id")
    public String findIdView(){
        return "/certification/find-id";
    }
    @GetMapping("/find-pw")
    public String findPwView(){
        return "/certification/find-pw";
    }
    @GetMapping("/change-pw")
    public String changePwView(){
        return "/certification/change-pw";
    }

}
