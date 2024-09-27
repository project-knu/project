package AELE.backend.cctv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class loginController {

    /*
    private final UserRepository userRepository;

    @Autowired
    loginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
*/


    @GetMapping("/login")
    String login(){
        return "login";
    }
    @GetMapping("/main")
    String main(){
        return "main";
    }
}
