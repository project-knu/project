package AELE.backend.cctv.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;
    private String url;
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    private Long userId;

    private String description;

    private LocalDateTime createdAt;

    private String location;

    public Video(){}
    public Video(String url, String name, Long user_id){
        this.url = url;
        this.name = name;
        this.userId = user_id;
    }

}
