package AELE.backend.cctv.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true)
    private String email;

    private String provider;
    private String providerId;

    private String name;

    public User(){

    }
    public User(String email){
        this.email = email;
    }

}
