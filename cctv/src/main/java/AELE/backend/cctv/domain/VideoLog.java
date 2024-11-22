package AELE.backend.cctv.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class VideoLog {// video 내에서 특정 이벤트들이 발생하면 그 사건들에 대한 시간, 이미지가분석한 content 기록

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))// 데이터 무결성 강제해서 삭제나 수정이 힘들어져서 일부로 끔
    private Video video;

    private String content; // 여기에 뭐가 들어갈꺼지? 나중에 질문하

    private LocalDateTime time; // 이벤트가 일어난 시간

}
