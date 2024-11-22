package AELE.backend.cctv.DTO;

import AELE.backend.cctv.domain.Video;
import AELE.backend.cctv.domain.VideoSummary;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class VideoDTO {

    // 영상 리스트 응답 DTO
    private Long videoId;
    private String name;
    private String url;
    private LocalDateTime createdAt;
    private String summaryContent; // 보고서 내용

    public static VideoDTO toDTO(Video v, VideoSummary vs) {
        return VideoDTO.builder()
                .videoId(v.getId())
                .name(v.getName())
                .url(v.getUrl())
                .createdAt(v.getCreatedAt())
                .summaryContent(vs.getContent())
                .build();
    }
}
