package AELE.backend.cctv.repository;

import AELE.backend.cctv.domain.Video;
import AELE.backend.cctv.domain.VideoSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VideoSummaryRepository extends JpaRepository<VideoSummary, Long> {

    /* 여기있는 코드들 모두 N+1 문제 유발합니다. 바꿔야함
    public Optional<VideoSummary> findByVideo_Id(Long videoId);
    public List<VideoSummary> findAllByVideoInAndContentContaining(List<Video> videoList, String content);
    public List<VideoSummary> findAllByVideoInAndCreatedAtBetween(List<Video> videoList, LocalDateTime start, LocalDateTime end);
    public List<VideoSummary> findAllByVideoInAndContentContainingAndCreatedAtBetween(List<Video> videoList, String content, LocalDateTime start, LocalDateTime end);
*/
    // 해결버전들
    @Query(value = "SELECT vs FROM VideoSummary vs JOIN FETCH vs.video v WHERE v.id = :videoId")
    Optional<VideoSummary> findByVideo_Id(@Param("videoId") Long videoId);

    @Query(value = "SELECT vs FROM VideoSummary vs JOIN FETCH vs.video v WHERE vs.content LIKE %:content% AND v.userId = :userId")
    List<VideoSummary> findAllByContentContainingAndUserId(@Param("content") String content, @Param("userId") Long userId);
    @Query(value = "SELECT vs FROM VideoSummary vs JOIN FETCH vs.video v WHERE vs.createdAt BETWEEN :startDate AND :endDate AND v.userId = :userId")
    List<VideoSummary> findAllByCreatedAtBetweenAndUserId(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT vs FROM VideoSummary vs JOIN FETCH vs.video v WHERE vs.content LIKE %:content% AND vs.createdAt BETWEEN :startDate AND :endDate AND v.userId = :userId")
    List<VideoSummary> findAllByContentContainingAndCreatedAtBetweenAndUserId(
            @Param("content") String content,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("userId") Long userId,
            Pageable pageable);

}
