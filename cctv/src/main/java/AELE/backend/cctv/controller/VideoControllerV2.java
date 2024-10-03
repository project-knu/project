package AELE.backend.cctv.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/videos")
@Transactional
public class VideoControllerV2 {

    // 응답 데이터로 VideoDTO / VideoLogDTO 보냄 (다른 것들도 나중에 DTO 변경 고려)
    // 응답 데이터 줄 때 https://wildeveloperetrain.tistory.com/240
    // 위 링크에 'JSON Body만 사용하는 방식 (HTTP Status Code는 항상 200)' 참고하면 좋을 거 같습니다 (아직 구현은 안 했음)
    // Pageable - page size sort 추가 고려 (pageDTO) - 마지막에 테스트 함수 있음

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final VideoLogRepository videoLogRepository;
    private final VideoSummaryRepository videoSummaryRepository;

    // 임시 유저 -> 진짜 로그인한 유저로 변경해야 함
    private User tempUser() {
        return userRepository.findById(100L).orElse(null);
    }

    /**
     * 영상 목록 조회
     */
    @GetMapping
    public List<VideoDTO> videoList() {
        return videoRepository.findAllByUserId(tempUser().getId()).stream().map(VideoDTO::toDTO).toList();
    }

    /**
     * 영상 검색 : 텍스트 / 조회
     */
    @GetMapping("/search")
    public List<VideoDTO> videoSearch(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "start", required = false, defaultValue = "1970-01-01") LocalDate start,
            @RequestParam(value = "end", required = false, defaultValue = "2999-12-31") LocalDate end
    ) {
        LocalDateTime startTime = start.atStartOfDay();
        LocalDateTime endTime = end.atTime(LocalTime.MAX);

        List<Video> videoList = videoRepository.findAllByUserId(tempUser().getId());

        // 텍스트 검색만
        // List<VideoSummary> videoSummaryList = videoSummaryRepository.findAllByVideoInAndContentContaining(videoList, keyword);
        // 날짜 검색만 startDate ~ endDate 범위의 video 찾기
        // List<VideoSummary> videoSummaryList = videoSummaryRepository.findAllByVideoInAndCreatedAtBetween(videoList, startTime, endTime);

        // 텍스트 + 날짜 검색
        List<VideoSummary> videoSummaryList = videoSummaryRepository.findAllByVideoInAndContentContainingAndCreatedAtBetween(videoList, keyword, startTime, endTime);

        List<Long> video_ids = new ArrayList<>();
        videoSummaryList.iterator().forEachRemaining(
                videoSummary -> { video_ids.add(videoSummary.getVideo().getId());}
        );
        return videoRepository.findAllById(video_ids).stream().map(VideoDTO::toDTO).toList();
    }

    /**
     * 영상 상세 조회 - 영상-유저 인증
     */
    @GetMapping("/{videoId}")
    public VideoDTO videoDetail(@PathVariable("videoId") Long videoId) {
        Optional<Video> video = videoRepository.findById(videoId);
        if (video.isPresent() && checkUserWithVideo(tempUser(), video.get())) {
            return VideoDTO.toDTO(video.get());
        }
        return null;
    }

    /**
     * 로그 목록 조회 & 로그 검색
     */
    @GetMapping("/{videoId}/logs")
    public List<VideoLogDTO> videoLogList(
            @PathVariable("videoId") Long videoId,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        List<VideoLog> logList
                = (keyword == null) ?
                videoLogRepository.findAllByVideo_Id(videoId) :
                videoLogRepository.findAllByVideo_IdAndContentContaining(videoId, keyword);
        if (!logList.isEmpty() && checkUserWithVideo(tempUser(), logList.get(0).getVideo())) {
            return logList.stream().map(VideoLogDTO::toDTO).toList();
        }
        return null;
    }

    /**
     * 로그 상세 조회
     */
    @GetMapping("/{videoId}/logs/{logId}")
    public VideoLogDTO videoLogDetail(@PathVariable("videoId") Long videoId, @PathVariable("logId") Long logId) {
        Optional<VideoLog> log = videoLogRepository.findById(logId);
        if (log.isPresent() && checkUserWithVideo(tempUser(), log.get().getVideo())) {
            return VideoLogDTO.toDTO(log.get());
        }
        return null;
    }

    /**
     * 보고서 조회
     */
    @GetMapping("/{videoId}/summary")
    public VideoSummaryDTO videoSummary(@PathVariable("videoId") Long videoId) {
        Optional<VideoSummary> summary = videoSummaryRepository.findByVideo_Id(videoId);
        if (summary.isPresent() && checkUserWithVideo(tempUser(), summary.get().getVideo())) {
            return VideoSummaryDTO.toDTO(summary.get());
        }
        return null;
    }

    /**
     *  보고서 수정 : title , content 수정
     */
    @PostMapping("/{videoId}/summary/edit")
    public VideoSummaryDTO editVideoSummary(@PathVariable("videoId") Long videoId, @RequestBody VideoSummaryForm form) {
        Optional<VideoSummary> summary = videoSummaryRepository.findById(form.getId());
        if (summary.isPresent() && checkUserWithVideo(tempUser(), summary.get().getVideo())) {
            summary.get().update(form.getTitle(), form.getContent());
            return VideoSummaryDTO.toDTO(summary.get());
        }
        return null;
    }

    // 유저의 비디오인지 체크
    private boolean checkUserWithVideo(User user, Video video) {
//        return video.getUser().equals(user);
        return video.getUserId().equals(user.getId());
    }

    // pageDTO 테스트
    @GetMapping("/pageTest")
    public List<User> pageTest(@ModelAttribute PageDTO pageDTO) {
        pageDTO.setCriteria("id");
        Pageable pageable = PageDTO.toPageRequest(pageDTO);
        List<User> content = userRepository.findAll(pageable).getContent();
        return content;
    }

}
