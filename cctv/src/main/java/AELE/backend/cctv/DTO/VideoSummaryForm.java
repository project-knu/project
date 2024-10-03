package AELE.backend.cctv.DTO;

import AELE.backend.cctv.domain.VideoSummary;
import lombok.Getter;

@Getter
public class VideoSummaryForm {

    private Long id;

    private String title;

    private String content;

}
