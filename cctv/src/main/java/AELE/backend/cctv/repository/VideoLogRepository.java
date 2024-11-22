package AELE.backend.cctv.repository;

import AELE.backend.cctv.domain.VideoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoLogRepository extends JpaRepository<VideoLog, Long> { // 나중에 여기에서 full text search를 해야한다고 판단되면 수정해야함

    /* N+1 문제 발생하는 코드라서 일부러 주석 처리함
    public List<VideoLog> findAllByVideo_Id(Long videoId);
    public List<VideoLog> findAllByVideo_IdAndContentContaining(Long videoId, String content);
    */


    //해결 버전
    // N+1 문제 해결할려면 직접 query 문법을 짜야함(정확히는 jpql을 )
    @Query(value = "SELECT vl FROM VideoLog vl JOIN FETCH vl.video v WHERE v.id = :videoId")
    public List<VideoLog> findAllByVideo(@Param("videoId") Long videoId);
    @Query(value = "SELECT vl FROM VideoLog vl JOIN FETCH vl.video v WHERE v.id = :videoId AND vl.content LIKE %:content%")
    List<VideoLog> findAllByVideo_IdAndContentContaining(@Param("videoId") Long videoId, @Param("content") String content);
}
