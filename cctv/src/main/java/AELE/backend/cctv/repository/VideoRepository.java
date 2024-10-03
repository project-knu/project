package AELE.backend.cctv.repository;

import AELE.backend.cctv.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video,Long> {
    Optional<Video> findByUserIdAndName(Long userId, String name);

    public List<Video> findAllByUserId(Long userId);
    
    // Video 엔티티 클래스에 User user 를 둘 때
//    public List<Video> findAllByUser(User user);
//    public List<Video> findAllByUser_Id(Long user_id);
}
