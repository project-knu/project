package AELE.backend.cctv.DTO;

import AELE.backend.cctv.domain.VideoLog;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class VideoLogDTO {

    private Long id;

    private VideoDTO videoDTO;

    private String content;

    private LocalDateTime time;

    public static VideoLogDTO toDTO(VideoLog log) {
        return VideoLogDTO.builder()
                .id(log.getId())
                .videoDTO(VideoDTO.toDTO(log.getVideo()))
                .content(log.getContent())
                .time(log.getTime())
                .build();
    }

}
