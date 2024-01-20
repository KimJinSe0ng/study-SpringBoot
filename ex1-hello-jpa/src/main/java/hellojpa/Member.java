package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity //JPA가 로딩될 때 JPA가 관리해야겠다고 인식
//@Table(name = "USER") //데이터베이스 USER 테이블과 자동 매핑: 기본값은 클래스명, 예를 들어 쿼리가 from USER 이렇게 바뀜
public class Member {

    @Id
    private String id;

    @Column(name = "name") //DB 컬럼명은 name 이야 하면 name으로 Insert 함
    private String username;
    private Integer age; //Integer하면 DB에서 Integer에 적절한 숫자 타입으로 만들어줌

    @Enumerated(EnumType.STRING) //DB엔 Enum 타입이 없는데 이걸 쓰면 된다, 반드시 STRING으로 써야 함!!!
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //LocalDate, LocalDateTime으로 대체 가능
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP) //LocalDate, LocalDateTime으로 대체 가능
    private Date lastModifiedDate;

    private LocalDate testLocalDate; //LocalDate: 연월
    private LocalDateTime testLocalDateTime; //LocalDateTime: 연월일
    @Lob
    private String description;
    @Transient //DB 말고 메모리에서만 쓰고 싶을 때, 특정 필드를 컬럼에 매핑하지 않음
    private Integer temp;

    public Member() { //JPA는 기본적으로 내부적으로 리플렉션도 쓰기 때문에 동적으로 객체를 생성해내기 때문에 기본 생성자 필요
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }
}
