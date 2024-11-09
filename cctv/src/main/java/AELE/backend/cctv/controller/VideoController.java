package AELE.backend.cctv.controller;

import AELE.backend.cctv.auth.CustomOAuth2User;
import AELE.backend.cctv.DTO.*;
import AELE.backend.cctv.domain.User;
import AELE.backend.cctv.domain.Video;
import AELE.backend.cctv.domain.VideoLog;
import AELE.backend.cctv.domain.VideoSummary;
import AELE.backend.cctv.repository.UserRepository;
import AELE.backend.cctv.repository.VideoLogRepository;
import AELE.backend.cctv.repository.VideoRepository;
import AELE.backend.cctv.repository.VideoSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
//@ResponseBody
//@RequestMapping("/videos")
//@Transactional
public class VideoController {

    /*
     응답 데이터로 VideoDTO / VideoLogDTO 보냄 (다른 것들도 나중에 DTO 변경 고려)
     응답 데이터 줄 때 https://wildeveloperetrain.tistory.com/240
     위 링크에 'JSON Body만 사용하는 방식 (HTTP Status Code는 항상 200)' 참고하면 좋을 거 같습니다 (아직 구현은 안 했음)
     Pageable - page size sort 추가 고려 (pageDTO) - 마지막에 테스트 함수 있음


    추가) test 해보려고 html로 redirect 시켰음(이게 더 빠르게 확인 가능해서 나중에 react랑 연동할 때는 return 값만 바꾸면 됨
         그리고 내가 봤을 때 이해 안되는 부분은 주석 처리 한다음 일요일날 마지막으로 구현하는게 목표
         그리고 위에 어노테이션 몇개 주석 처리했으니까 나중에 확인
    */


    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final VideoLogRepository videoLogRepository;
    private final VideoSummaryRepository videoSummaryRepository;

    @GetMapping("/list")
    public String video_list(@AuthenticationPrincipal CustomOAuth2User customUser, Model model, @ModelAttribute PageDTO pageDTO){ // 동영상 list 보여줌
        User user = customUser.getUser(); // custom해서 처음으로 사용하는 따근 따끈한 user, 여기에 user 정보 다 집어 넣음, 그리고 정보는 무조건 들어있습니다

        // front_end가 지은 페이지 디자인에서는 video url들의 list, 생성 날짜, 보고서 이름, 보거서 내용 필요하나
        // 일단은 video_url, video_name, video_date만 보내자

        // one to many 하는 방법으로 가져오되, 쿼리문으로 만든 방법 써서 N+1문제 해결하고, 보고서 내용도 같이 가져옴
        Pageable pageable = pageDTO.getP() == 1 ? PageDTO.toPageRequest(pageDTO) : null;
        List<Video> video_list = videoRepository.findAllByUserIdWithSummary(user.getId(), pageable);

        List<VideoDTO> videoDTOList = new ArrayList<>(); // 정보를 보내줄 DTO
        for(Video v : video_list){// 여기서 마지막에 videoSummary content를 안한건 보고서 생성은 AI가 해줘야하는데, 아직 구현을 안해서 그냥 암거나 집어 넣음
            videoDTOList.add(VideoDTO.toDTO(v.getId(),v.getName(),v.getUrl(),v.getCreatedAt(),v.getVideoSummary().getContent()));
        }

        model.addAttribute("videos",videoDTOList);

        return "list";
    }
    @GetMapping("/detail/{videoId}")
    public String video_detail(@AuthenticationPrincipal CustomOAuth2User customUser, @PathVariable Long videoId, Model model) throws Exception { // 동영상 새부 내용

        User user = customUser.getUser();
        Optional<Video> v = videoRepository.findByIdAndUserIdWithSummary(videoId,user.getId());
        if(v.isEmpty()) {
            throw new Exception();
        }
        Video video = v.get();

        List<VideoLog> videoLogs = new ArrayList<>();// 이것도 AI와 연동해서 만들어야하는데 아직 연동이 안되었기에 그냥 빈 친구 넣어서 줄꺼임

        VideoDTO2 videoDTO2 = VideoDTO2.toDTO2(video.getId(),video.getName(),video.getUrl(),video.getCreatedAt(),video.getVideoSummary().getContent(),videoLogs);
        model.addAttribute("video",videoDTO2);

        return "detail";
    }


    //  영상 검색에는 날짜 검색 or content 검색 이 2가지로 분류
    @GetMapping("/search")
    public String videoSearch( // 여기서 String으로 redirect 시키는건 개발할 때 한눈에 보기 쉽게 할려고 하는거임. 나중에 구현할 때는 json 방식으로 데이터 보내서 react에서 처리하도록 할꺼임
                                       @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                       @RequestParam(value = "start", required = false, defaultValue = "1970-01-01") LocalDate start,
                                       @RequestParam(value = "end", required = false, defaultValue = "2999-12-31") LocalDate end,
                                       @AuthenticationPrincipal CustomOAuth2User customUser, Model model, @ModelAttribute PageDTO pageDTO
    )
    {

        // keyword + 날짜 아무것도 안정했으면 브라우저에서 그냥 data 안보내게 설정해놓을 꺼니, 무조건 keyword나 날짜 둘중 한개는 들어와 있음

        User user = customUser.getUser();

        LocalDateTime starttime = start.atStartOfDay(); // 정한 시작일의 0시 0분 0초 이거
        LocalDateTime endtime = end.atTime(LocalTime.MAX); // 정한 끝나는 날의 23:59:59 이거 정하고

        Pageable pageable = (pageDTO.getP() == 1) ?  PageDTO.toPageRequest(pageDTO) : null;

        List<VideoSummary> videoSummaryList;

        if(keyword.isEmpty()) { // 그냥 날짜로만 검색
            videoSummaryList = videoSummaryRepository.findAllByCreatedAtBetweenAndUserId(starttime,endtime,user.getId(), pageable);
        }
        else{ // 보고서 내용으로만 검색
            videoSummaryList = videoSummaryRepository.findAllByContentContainingAndCreatedAtBetweenAndUserId(keyword,starttime,endtime,user.getId(), pageable);
        }

        List<VideoDTO> videoDTOList = new ArrayList<VideoDTO>();

        for (VideoSummary vs : videoSummaryList) {
            Video video = vs.getVideo(); // VideoSummary에서 Video 가져옴
            VideoDTO dto = VideoDTO.toDTO(video.getId(), video.getName(), video.getUrl(), video.getCreatedAt(),vs.getContent());
            videoDTOList.add(dto); // DTO 리스트에 추가
        }

        model.addAttribute("videos", videoDTOList);

        return "list";
    }


/*
    /* 이건 일단 주석처리하고 만들죠

    //보고서 수정 : title , content 수정
    @PostMapping("/{videoId}/summary/edit")
    public VideoSummaryDTO editVideoSummary(@PathVariable("videoId") Long videoId, @RequestBody VideoSummaryForm form) {
        Optional<VideoSummary> summary = videoSummaryRepository.findById(form.getId());
        if (summary.isPresent() && checkUserWithVideo(tempUser(), summary.get().getVideo())) {
            summary.get().update(form.getTitle(), form.getContent());
            return VideoSummaryDTO.toDTO(summary.get());
        }
        return null;
    }
*/
}
