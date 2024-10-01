package AELE.backend.cctv;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String url;
    public String name;

    public Long userId;

    public Video(){}
    public Video(String url, String name, Long user_id){
        this.url = url;
        this.name = name;
        this.userId = user_id;
    }

}
