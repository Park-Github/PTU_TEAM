package SpringProject.WebCommunity.Config;

import SpringProject.WebCommunity.Repository.MemberRepos;
import SpringProject.WebCommunity.Service.MemberDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SecurityContextRepository scr,
            RememberMeServices rms
    ) throws Exception {
        http.httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(c -> c
                        // 기본 리소스 접근 허용
                        .requestMatchers(antMatcher("/css/**")).permitAll()
                        .requestMatchers(antMatcher("/img/**")).permitAll()
                        .requestMatchers(antMatcher("/js/**")).permitAll()
                        .requestMatchers(antMatcher("/error/**")).permitAll()

                        // 사용자 인증 요청 허용
                        .requestMatchers(antMatcher("/login")).permitAll()
                        .requestMatchers(antMatcher("/register")).permitAll()
                        .requestMatchers(antMatcher("/logout")).permitAll()

                        // 공개 페이지 허용
//                        .requestMatchers(antMatcher("/")).permitAll()
                                .requestMatchers(antMatcher("/board/form/**")).authenticated()
                                .requestMatchers(antMatcher("/market/form/**")).authenticated()

                                // 위에 명시되지 않은 요청은 인증된 사용자를 제외하고 불허
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .securityContext(ctx -> ctx.securityContextRepository(scr))
                .rememberMe(me -> me.rememberMeServices(rms))
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(antMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public RememberMeServices rememberMeServices(
            TokenProperties tokenProperties,
            UserDetailsService userDetailsService
    ) {
        String key = tokenProperties.getRememberMeToken();
        var sha256 = RememberMeTokenAlgorithm.SHA256;
        var services = new TokenBasedRememberMeServices(key, userDetailsService, sha256);

        services.setMatchingAlgorithm(sha256);
        services.setParameter("remember-me");
        services.setTokenValiditySeconds(2592000);  // 30 days
        return services;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberRepos memberRepos) {
        return new MemberDetailsService(memberRepos);
    }

    @Bean
    public MemberDetailsService memberDetailsService(MemberRepos memberRepos) {
        return new MemberDetailsService(memberRepos);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
