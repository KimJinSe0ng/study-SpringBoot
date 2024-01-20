package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //JPA가 로딩될 때 JPA가 관리해야겠다고 인식
@Table(name = "USER") //데이터베이스 USER 테이블과 자동 매핑 : 기본값은 클래스명, 예를 들어 쿼리가 from USER 이렇게 바뀜
public class Member {

    @Id
    private Long id;
//    @Column(name = "username") // username 컬럼과 자동 매핑 : 기본값은 변수명
    private String name;

    public Member() { //JPA는 기본 생성자가 필수 - 동적으로 객체를 생성해야하는 경우가 있음
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
