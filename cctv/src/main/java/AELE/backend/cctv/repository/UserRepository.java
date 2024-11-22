package AELE.backend.cctv.repository;

import AELE.backend.cctv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>  {
    Optional<User> findByEmail(String email);// email로 찾을 수 있게 해달라고 소원을 빌었습니다
}
