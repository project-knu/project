package AELE.backend.cctv.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


/* 이거는 사장된 방식이니까 그만 좀 쓰십시요
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler successHandler;

    @Autowired
    public SecurityConfig(CustomOAuth2SuccessHandler successHandler, CustomOAuth2UserService customOAuth2UserService) {
        this.successHandler = successHandler;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //crsf 비활성화, 개발할 때는 잠간 꺼둘 생각
        http.csrf((csrf) -> csrf.disable());

        //
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/", "/login.html", "/css/*", "/assets/*").permitAll().anyRequest().authenticated())
                        //authorize.requestMatchers("*", "/*","/*/*").permitAll().anyRequest().authenticated())

                .oauth2Login((oauth2) -> oauth2.loginPage("/login.html")
                                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))// 커스텀 OAuth2UserService 등록 + 이때 데이터 베이스 저장도 같이 진행하도록 함
                                //.successHandler(successHandler) // 성공 핸들러 등록했으니
                                .defaultSuccessUrl("/index.html",true) // defulat 핸들러는 뺴주자
                                .failureUrl("/login.html?error=true")

                        )
                .logout((logout) -> logout
                        .logoutUrl("/logout")  // 로그아웃 요청 URL
                        .logoutSuccessUrl("/login.html")  // 로그아웃 성공 후 리디렉션 경로
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}