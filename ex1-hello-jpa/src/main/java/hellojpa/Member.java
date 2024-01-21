package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity //JPA가 로딩될 때 JPA가 관리해야겠다고 인식
//@Table(name = "USER") //데이터베이스 USER 테이블과 자동 매핑: 기본값은 클래스명, 예를 들어 쿼리가 from USER 이렇게 바뀜
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq")
//@TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCE",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 1
//)
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
//        initialValue = 1, allocationSize = 50)
public class Member {

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME") //DB 컬럼명은 name 이야 하면 name으로 Insert 함
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;
    @ManyToOne
    @JoinColumn(name = "TEAM_ID") //Member 객체의 Team team 레퍼런스와 MEMBER 테이블의 TEAM_ID(FK)와 매핑해야 함
    private Team team; //JPA에게 이 둘의(Member, Team) 관계가 1:다 인지 다:1 인지 알려줘야 함

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
