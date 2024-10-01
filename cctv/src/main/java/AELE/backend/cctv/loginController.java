package AELE.backend.cctv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class loginController {


    private final UserRepository userRepository;

    @Autowired // 이거 귀찮으면 롬복 써도 되는데 팀들이 쓰는지 안쓰는지 몰라서 일단 안썼습니다.
    loginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/")public String index(){return "login";}
    @GetMapping("/a") public String a(){return "a";}
    @GetMapping("/login") public String login(){
        return "login";
    }

    @GetMapping("/main")
    public String main_page(OAuth2AuthenticationToken authentication, Model model){
        //여기에서 나중에 데이터 베이스 조회해서 자기가 올린 영상들 한번에 보여주도록 arraylist에 담아서 주는 것도 좋을 듯

        //일단 여기에는 login시 이름만 보이게 설정해 놓겠
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauthToken.getPrincipal();
        String email = oauth2User.getAttribute("email");
        model.addAttribute("email", email);
        return "main";
    }
}

// 나중에 정보 보낼 떄 한꺼번에 보내기 위한 dto clss 일단 만들어 두기만 했어요
class dto_user {
    public String email;
}