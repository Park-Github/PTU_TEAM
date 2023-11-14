package SpringProject.WebCommunity;

import SpringProject.WebCommunity.Config.QueryDSLConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@Import(QueryDSLConfiguration.class)
class WebCommunityApplicationTests {

}
