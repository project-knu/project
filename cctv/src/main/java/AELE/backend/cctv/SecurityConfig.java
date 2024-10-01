package AELE.backend.cctv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


/* 이거는 사장된 방식이니까 그만 좀 쓰십시요
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2SuccessHandler successHandler;

    @Autowired
    public SecurityConfig(CustomOAuth2SuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //crsf 비활성화, 개발할 때는 잠간 꺼둘 생각
        http.csrf((csrf) -> csrf.disable());

        //
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/","/a", "/login").permitAll().anyRequest().authenticated())
                        .oauth2Login((oauth2) -> oauth2.loginPage("/login")
                                .successHandler(successHandler) // 성공 핸들러 등록했으니
                                //.defaultSuccessUrl("/main",true) // defulat 핸들러는 뺴주자
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