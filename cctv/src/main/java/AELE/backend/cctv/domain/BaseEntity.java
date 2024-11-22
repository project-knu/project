package AELE.backend.cctv.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor // 기본 생성자 자동 생성해, 롬복문법임. 그냥 생성자 쓰면 되지만 귀찮으니까
@EntityListeners(AuditingEntityListener.class)// 생성되거나 수정될 때 자동으로 시간을 기록하도록 설정합니다.
public abstract class BaseEntity {

    @CreatedDate // 자동으로 생성된 시간 기록해줌
    protected LocalDateTime createdAt;

    @LastModifiedDate // 자동으로 수정된 시간 기록해줌
    protected LocalDateTime modifiedAt;

}
