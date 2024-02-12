package study.datajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class JpaBaseEntity {

    @Column(updatable = false) //createdDate값 바꿔도 DB에 변경되지 않음
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist //persist하기 전에 이벤트 발생
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate //업데이트 할 때 이벤트 발생
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
