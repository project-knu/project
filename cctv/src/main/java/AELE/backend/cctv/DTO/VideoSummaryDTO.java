/*
package AELE.backend.cctv.DTO;
import AELE.backend.cctv.domain.VideoSummary;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Builder
@Data
public class VideoSummaryDTO {
    private Long id;
    private VideoDTO videoDTO;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    public static VideoSummaryDTO toDTO(VideoSummary summary) {
        return VideoSummaryDTO.builder()
                .id(summary.getId())
                .videoDTO(VideoDTO.toDTO(summary.getVideo()))
                .title(summary.getTitle())
                .content(summary.getContent())
                .createdAt(summary.getCreatedAt())
                .modifiedAt(summary.getModifiedAt())
                .build();
    }
}
*/