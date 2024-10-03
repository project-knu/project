package AELE.backend.cctv.DTO;

import AELE.backend.cctv.domain.Video;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class VideoDTO {

    // 영상 응답 DTO

    private Long id;

    private String name;

    private String description;

    private String url;

    private LocalDateTime createdAt;

    private String location;

    public static VideoDTO toDTO(Video video) {
        return VideoDTO.builder()
                .id(video.getId())
                .name(video.getName())
                .description(video.getDescription())
                .url(video.getUrl())
                .createdAt(video.getCreatedAt())
                .location(video.getLocation())
                .build();
    }

}
