package SpringProject.WebCommunity.Config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.tokens")
public class TokenProperties {

    public String rememberMeToken;

}
