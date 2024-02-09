package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> { //스프링 데이터 JPA가 구현 클래스를를 알아서 생성해서 주입해줌
}
