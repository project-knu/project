package AELE.backend.cctv;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true)
    public String email;

    public User(){

    }
    public User(String email){
        this.email = email;
    }

}
