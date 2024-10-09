package AELE.backend.cctv.DTO;

import AELE.backend.cctv.domain.Video;
import AELE.backend.cctv.domain.VideoLog;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class VideoDTO2 { // video_log, video_summary dto는 필요 없는거 같아 그냥 없애고 여기에 다 몰아 넣었습니다.(어차피 나중에 보낼 때 이걸로만 보낼꺼임)

    // 영상 응답 DTO

    //private Long id; 이거 필요없음. 서버에서 html로 보낼 때 필요한 것만 보낼꺼임
    private Long videoId; // 생각해보니 필요할 것같음. 어차피 보안적이 문제에서 videoId만 알려주는건 크게 문제가 없다고 판단되니 그냥 보내자
    private String videoName;
    private String url;
    private LocalDateTime createdAt;
    private String videoSummaryContent;// 보고서 내용
    private List<VideoLog> videologList;

    //private String description; //private String location; // 얘네들은 필요 없는게 아니라 이작 구현을 안해서 주석처리 한것

    public static VideoDTO2 toDTO2(Long videoId,String videoName, String url, LocalDateTime createdAt, String videoSummaryContent, List<VideoLog> videologList) { // 필요한 정보만 보내기
        return VideoDTO2.builder()
                .videoId(videoId)
                .videoName(videoName)
                .url(url)
                .createdAt(createdAt)
                .videoSummaryContent(videoSummaryContent)
                .videologList(videologList)
                .build();
    }
}
