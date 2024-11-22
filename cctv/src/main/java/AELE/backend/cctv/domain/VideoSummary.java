package AELE.backend.cctv.domain;

import jakarta.persistence.*;
import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Getter
public class VideoSummary extends BaseEntity { // 동영상에 들어간 보고서, baseEntity 써서, 수정 날짜 + 생서 날짜 포괄적으로 기줌

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "summary_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // one to one 이 맞음. 보고서 하나당 동영상 하나 + one에서 하나가 삭제되면 나머지도 삭제시킴
    @JoinColumn(name = "video_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))// 데이터 무결성 강제해서 삭제나 수정이 힘들어져서 일부로 끔
    @JsonIgnore // 순환 참조 방지
    private Video video;

    private String content; // 보고서 내용

    public VideoSummary(){}
    public VideoSummary(Video video,String content) {
        this.video = video;
        this.content = content;
    }
    public void update(String content) {
        this.content = content;
    }


}
