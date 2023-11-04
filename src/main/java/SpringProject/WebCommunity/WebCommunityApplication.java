package SpringProject.WebCommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Import(QuerydslConfiguration.class)
@SpringBootApplication
public class WebCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCommunityApplication.class, args);
	}

}
