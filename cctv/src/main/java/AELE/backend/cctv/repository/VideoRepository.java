package AELE.backend.cctv.repository;

import AELE.backend.cctv.domain.Video;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video,Long> {
    Optional<Video> findByUserIdAndName(Long userId, String name); // user Id와 name으로 검색할때, 2개다 충족되야 반환
    List<Video> findAllByUserId(Long id);

    Optional<Video> findByIdAndUserId(Long id, Long userId);// user id 와 동영상 id 검색

    @Query("SELECT v FROM Video v JOIN FETCH v.videoSummary WHERE v.id = :id AND v.userId = :userId")// 이건 video SUmmary도 가져오도록
    Optional<Video> findByIdAndUserIdWithSummary(@Param("id") Long id, @Param("userId") Long userId);

    // Query로 Video와 VideoSummary를 한번에 조회하기 (디폴트로 영상 생성시간 순으로 정렬)
    @Query("SELECT v FROM Video v JOIN FETCH v.videoSummary WHERE v.userId = :userId order by v.createdAt desc")
    List<Video> findAllByUserIdWithSummary(@Param("userId") Long userId, Pageable pageable);

}
