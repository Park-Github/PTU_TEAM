package SpringProject.WebCommunity;

import SpringProject.WebCommunity.Config.QueryDSLConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(QueryDSLConfiguration.class)
@SpringBootApplication
public class WebCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCommunityApplication.class, args);
	}

}
