package AELE.backend.cctv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //crsf 비활성화, 개발할 때는 잠간 꺼둘 생각
        http.csrf((csrf) -> csrf.disable());

        //
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll())
                        .oauth2Login((oauth2) -> oauth2.loginPage("/login")
                                .defaultSuccessUrl("/main",true)
                                .failureUrl("/login?error=true")

                        )
                .logout((logout) -> logout
                        .logoutUrl("/logout")  // 로그아웃 요청 URL
                        .logoutSuccessUrl("/login")  // 로그아웃 성공 후 리디렉션 경로
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}