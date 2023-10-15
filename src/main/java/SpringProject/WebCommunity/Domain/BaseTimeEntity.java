package SpringProject.WebCommunity.Domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity가 해당 클래스를 상속시 필드도 column으로 인식
@EntityListeners(AuditingEntityListener.class) // 클래스에 Auditing 기능을 추가시킴
public class BaseTimeEntity {

    @CreatedDate // 엔티티 생성시 시간 자동 저장
    private LocalDateTime createdTime;
    @LastModifiedDate // 엔티티 수정시 시간 자동 저장
    private LocalDateTime revisedTime;

}

