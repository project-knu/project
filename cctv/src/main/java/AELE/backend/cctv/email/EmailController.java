package AELE.backend.cctv.email;

import AELE.backend.cctv.auth.CustomOAuth2User;
import AELE.backend.cctv.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send_email")
    String sendEmail(@AuthenticationPrincipal CustomOAuth2User customUser) {
        User user = customUser.getUser();
        String email = user.getEmail();

        emailService.sendEmail(email, "this is server oner", "bisang!!!!");
        return "redirect:/main";
    }

    @GetMapping("/sendtest")
    String mailpage(){
        return "sendtest";
    }
}
