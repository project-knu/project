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
import org.springframework.transaction.annotation.Transactional;
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
@ResponseBody
@Transactional
public class VideoController {

    /*
     응답 데이터 줄 때 https://wildeveloperetrain.tistory.com/240
     위 링크에 'JSON Body만 사용하는 방식 (HTTP Status Code는 항상 200)' 참고하면 좋을 거 같습니다

     * ResponseDTO 구현함
       프론트랑 연동할 때, 반환형 ResponseDTO로 바꾸고, return ResponseDTO.toDTO(VideoDTOList) 나 toDTO(videoDetailDTO) 하면 됨.


    추가) test 해보려고 html로 redirect 시켰음(이게 더 빠르게 확인 가능해서 나중에 react랑 연동할 때는 return 값만 바꾸면 됨
         그리고 내가 봤을 때 이해 안되는 부분은 주석 처리 한다음 일요일날 마지막으로 구현하는게 목표
         그리고 위에 어노테이션 몇개 주석 처리했으니까 나중에 확인
    */


    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final VideoLogRepository videoLogRepository;
    private final VideoSummaryRepository videoSummaryRepository;

    @GetMapping("/list")
//    public String video_list(@AuthenticationPrincipal CustomOAuth2User customUser, Model model, @ModelAttribute PageDTO pageDTO){
    public ResponseDTO video_list(@AuthenticationPrincipal CustomOAuth2User customUser, @ModelAttribute PageDTO pageDTO){

        User user = customUser.getUser();

        Pageable pageable = pageDTO.getP() == 1 ? PageDTO.toPageRequest(pageDTO) : null;
        List<Video> video_list = videoRepository.findAllByUserIdWithSummary(user.getId(), pageable);

        List<VideoDTO> videoDTOList = new ArrayList<>();
        for(Video v : video_list){
            videoDTOList.add(VideoDTO.toDTO(v, v.getVideoSummary()));
        }

        return ResponseDTO.toDTO(videoDTOList);

//        model.addAttribute("videos",videoDTOList);
//
//        return "list";
    }
    @GetMapping("/detail/{videoId}")
//    public String video_detail(@AuthenticationPrincipal CustomOAuth2User customUser, @PathVariable Long videoId, Model model) throws Exception { // 동영상 새부 내용
    public ResponseDTO video_detail(@AuthenticationPrincipal CustomOAuth2User customUser, @PathVariable Long videoId) throws Exception { // 동영상 새부 내용

        User user = customUser.getUser();
        Optional<Video> v = videoRepository.findByIdAndUserIdWithSummary(videoId,user.getId());
        if(v.isEmpty()) {
            throw new Exception(); // return ResponseDTO.toDTO("error", "such video not exists");
        }
        Video video = v.get();
        // 로그 가져올 때 시간 순으로 가져오기
        List<VideoLog> videoLogs = new ArrayList<>(); // 이것도 AI와 연동해서 만들어야하는데 아직 연동이 안되었기에 그냥 빈 친구 넣어서 줄꺼임

        VideoDetailDTO videoDetailDTO = VideoDetailDTO.toDTO(video, video.getVideoSummary(), videoLogs);

        return ResponseDTO.toDTO(videoDetailDTO);

//        model.addAttribute("video", videoDetailDTO);
//
//        return "detail";
    }


    //  영상 검색에는 날짜 검색 or content 검색 이 2가지로 분류
    @GetMapping("/search")

//    public String videoSearch(
    public ResponseDTO videoSearch(
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
            videoDTOList.add(VideoDTO.toDTO(vs.getVideo(), vs));
        }

        return ResponseDTO.toDTO(videoDTOList);


//        model.addAttribute("videos", videoDTOList);
//
//        return "list";
    }


    @PostMapping("/detail/{videoId}/edit")
    public ResponseDTO edit(@AuthenticationPrincipal CustomOAuth2User customUser, @PathVariable("videoId") Long videoId, @RequestBody VideoSummaryForm form) {
        User user = customUser.getUser();
        Optional<Video> v = videoRepository.findByIdAndUserIdWithSummary(videoId,user.getId());
        if(v.isEmpty()) {
            return ResponseDTO.toDTO("error", "no video");
        }
        Video video = v.get();
        VideoSummary videoSummary = video.getVideoSummary();

        video.update(form.getName());
        videoSummary.update(form.getContent());

        VideoDetailDTO videoDetailDTO = VideoDetailDTO.toDTO(video, videoSummary, new ArrayList<>());
        return ResponseDTO.toDTO(videoDetailDTO);
    }
}
