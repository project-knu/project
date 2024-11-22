package AELE.backend.cctv.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Video extends BaseEntity { // video에 대한 url과 이름

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "video_id")
    private Long id;

    private String url;
    private String name;

    //이거 일부러 manytoone 안한거에요. many to one 할 필요 없어요. 보안이나 이런거 때문에 @AuthenticationPrincipal OAuth2User principal로 유저정보 받아올꺼임
    private long userId;

    private String location;

    @OneToOne(mappedBy = "video", fetch = FetchType.LAZY)// 양방향 관계에서 VideoSummary와 연결
    private VideoSummary videoSummary;

    public Video(){}
    public Video(String url, String name, long userId, String location){
        this.url = url;
        this.name = name;
        this.userId = userId;
        this.location = location;
    }

    public void update(String name) {
        this.name = name;
    }

}
