package AELE.backend.cctv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class VideoController {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Autowired
    public VideoController(S3Service s3Service, VideoRepository videoRepository,UserRepository userRepository){
        this.s3Service = s3Service;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/upload")
    public String upload_page() {return "upload";}

    @GetMapping("/presigned-url")
    @ResponseBody
    public ResponseEntity<String> geturl(@AuthenticationPrincipal OAuth2User principal,@RequestParam String filename){// presigned-url을 요청하면 주는 함수
        String email = principal.getAttribute("email"); // 접속한 유저 email 가져오고
        Optional<User> user = userRepository.findByEmail(email);// email로 data base user 정보에서 가져오고

        if(user.isEmpty()){ // 사실 로그인한 유저에서 정보를 가져온거기 때문에, 로그인 할떄, 정보가 이미 있다. 그래서 오류가 뜰 것같지 않지만 일단 모르니까
            throw new IllegalArgumentException("유저를 찾을 수 없습니다.");
        }

        Optional<Video> video = videoRepository.findByUserIdAndName(user.get().id,filename);

        if(video.isEmpty()) {//  만약 일치하는 놈이 없다면
            String res = s3Service.createPresignedUrl(user.get().id + "/" + filename);//presigned를 받아와서, 단 이때 저장할 경로를 유저id/파일이름
            return ResponseEntity.ok(res);
        }
        //만약 일치하는게 있다면 안된다고 보내야함
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 파일이 있어서 추가할 수 없습니다.");
    }

    @PostMapping("/save")
    public String video_metadata_save(@AuthenticationPrincipal OAuth2User principal, @RequestBody Map<String, Object> videoMetadata){//POST로 오는 데이터를 받는 곳, 저장한 곳의 링크 + 올린파일 이름
        String url = (String) videoMetadata.get("url");
        String file_name = (String) videoMetadata.get("file_name");

        String email = principal.getAttribute("email");//로그인한 유저 정보(email) 가져오고
        Optional<User> user = userRepository.findByEmail(email);// 그걸로 data base에서 가져오고

        if(user.isEmpty()){ // 사실 로그인한 유저에서 정보를 가져온거기 때문에, 로그인 할떄, 정보가 이미 있다. 그래서 오류가 뜰 것같지 않지만 일단 모르니까
            throw new IllegalArgumentException("유저를 찾을 수 없습니다.");
        }

        System.out.println("url : " + url + " name : " + file_name);//출력 한번 해보기

        //이렇게 굳이 email로 안하고 id로 하는 이유는, 한번 데이터 베이스 뒤지는거에 비해서 데이터의 무결성이 올라가기 때문
        videoRepository.save(new Video(url,file_name,user.get().id)); // 이렇게 기존 video database에 있는지 없는지 check 안 하는 presigned를 발급할 때, 이미 이러한 이름이 없다는 것을 확인 했기 때문입니다
        return "redirect:/upload";
    }

}
