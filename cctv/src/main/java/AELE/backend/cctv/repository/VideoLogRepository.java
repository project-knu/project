package AELE.backend.cctv.repository;

import AELE.backend.cctv.domain.VideoLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoLogRepository extends JpaRepository<VideoLog, Long> {
    public List<VideoLog> findAllByVideo_Id(Long videoId);
    public List<VideoLog> findAllByVideo_IdAndContentContaining(Long videoId, String content);

}
