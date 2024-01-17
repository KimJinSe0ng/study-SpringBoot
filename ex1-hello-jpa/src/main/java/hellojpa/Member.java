package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity //JPA가 로딩될 때 JPA가 관리해야겠다고 인식
//@Table(name = "USER") // USER 테이블과 자동 매핑 : 기본값은 클래스명
public class Member {

    @Id
    private Long id;
//    @Column(name = "username") // username 컬럼과 자동 매핑 : 기본값은 변수명
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
