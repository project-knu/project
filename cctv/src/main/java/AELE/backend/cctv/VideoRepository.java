package AELE.backend.cctv;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video,Long> {
    Optional<Video> findByUserIdAndName(Long userId, String name);
}
