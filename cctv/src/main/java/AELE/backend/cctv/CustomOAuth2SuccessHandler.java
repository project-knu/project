package AELE.backend.cctv;

import AELE.backend.cctv.domain.User;
import AELE.backend.cctv.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;

    @Autowired
    public CustomOAuth2SuccessHandler(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauthToken.getPrincipal();

        // 유저 정보를 가져옴
        String email = oauth2User.getAttribute("email");

        System.out.println(" 여기는 custom " + email);

        // DB에 이미 해당 이메일이 있는지 확인하고 없다면 저장
        if (!userRepository.findByEmail(email).isPresent()) userRepository.save(new User(email));

        // 로그인 후 리다이렉트할 페이지로 이동
        response.sendRedirect("/main");
    }
}
