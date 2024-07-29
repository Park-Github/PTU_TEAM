package SpringProject.WebCommunity;

import SpringProject.WebCommunity.Config.QueryDSLConfiguration;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

@Import(QueryDSLConfiguration.class)
@SpringBootApplication
public class WebCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCommunityApplication.class, args);
	}

	@PostConstruct
	public void setTimeZone(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
