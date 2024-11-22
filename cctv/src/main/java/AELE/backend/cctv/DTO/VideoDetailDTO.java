package AELE.backend.cctv.DTO;

import AELE.backend.cctv.domain.Video;
import AELE.backend.cctv.domain.VideoLog;
import AELE.backend.cctv.domain.VideoSummary;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class VideoDetailDTO { 
    // 영상 상세 응답 DTO

    private Long videoId;
    private String name;
    private String url;
    private LocalDateTime createdAt;
    private String location;// cctv 위치
    private String summaryContent; // 보고서 내용
    private LocalDateTime summaryCreatedAt;
    private LocalDateTime summaryModifiedAt;
    private List<VideoLogDTO> logList;

    public static VideoDetailDTO toDTO(Video v, VideoSummary vs, List<VideoLog> logList) {
        return VideoDetailDTO.builder()
                .videoId(v.getId())
                .name(v.getName())
                .url(v.getUrl())
                .createdAt(v.getCreatedAt())
                .location("CCTV Location") // cctv 위치 추가 후 수정하기
                .summaryContent(vs.getContent())
                .summaryCreatedAt(vs.getCreatedAt())
                .summaryModifiedAt(vs.getModifiedAt())
                .logList(logList.stream().map(VideoLogDTO::toDTO).toList())
                .build();
    }

    @Builder
    @Data
    static class VideoLogDTO {
        private LocalDateTime time;
        private String content;
        static VideoLogDTO toDTO(VideoLog log) {
            return VideoLogDTO.builder()
                    .time(log.getTime())
                    .content(log.getContent())
                    .build();
        }
    }

}
