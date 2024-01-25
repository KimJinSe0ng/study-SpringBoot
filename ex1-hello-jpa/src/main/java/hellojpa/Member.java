package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity //JPA가 로딩될 때 JPA가 관리해야겠다고 인식
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME") //DB 컬럼명은 name 이야 하면 name으로 Insert 함
    private String username;

    @ManyToOne(fetch = FetchType.LAZY) //@ManyToOne, @OneToOne은 기본값이 즉시로딩이기 때문에 LAZY로 바꿔줘야 한다.
    @JoinColumn(name = "TEAM_ID")
    private Team team;

//    @OneToOne
//    @JoinColumn(name = "LOCKER_ID")
//    private Locker locker;
//
//    @OneToMany(mappedBy = "member")
//    private List<MemberProduct> memberProducts = new ArrayList<>();

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
