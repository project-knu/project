package AELE.backend.cctv.repository;

import AELE.backend.cctv.domain.Video;
import AELE.backend.cctv.domain.VideoSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VideoSummaryRepository extends JpaRepository<VideoSummary, Long> {

    public Optional<VideoSummary> findByVideo_Id(Long videoId);
    public List<VideoSummary> findAllByVideoInAndContentContaining(List<Video> videoList, String content);
    public List<VideoSummary> findAllByVideoInAndCreatedAtBetween(List<Video> videoList, LocalDateTime start, LocalDateTime end);
    public List<VideoSummary> findAllByVideoInAndContentContainingAndCreatedAtBetween(List<Video> videoList, String content, LocalDateTime start, LocalDateTime end);


}
