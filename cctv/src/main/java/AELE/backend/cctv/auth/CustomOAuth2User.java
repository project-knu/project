package AELE.backend.cctv.auth;

import AELE.backend.cctv.domain.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oAuth2User;
    private User user; // 데이터베이스에서 가져온 유저 정보

    public CustomOAuth2User(OAuth2User oAuth2User, User user) {
        this.oAuth2User = oAuth2User;
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities(); // 기본 권한을 그대로 사용
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public User getUser() { //롬복 getter 로 써도 되는데 가독성 때문에 그냥 넣었습
        return user;
    }
}