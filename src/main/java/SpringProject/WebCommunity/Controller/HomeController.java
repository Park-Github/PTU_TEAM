package SpringProject.WebCommunity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/post-view")
    public String welcomeHome(){
        return "post-view";
    }

    @GetMapping("/sign-up")
    public String signUp() { return "sign-up"; }

    @GetMapping("/sign-in")
    public String signIn() { return "sign-in"; }

    @GetMapping("/post-write")
    public String write() { return "post-write"; }
}
