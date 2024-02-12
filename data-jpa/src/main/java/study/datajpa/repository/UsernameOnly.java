package study.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly { //인터페이스만 만들면 스프링 데이터 JPA가 구현 클래스를 만들어서 인터페이스 갖고 인식해서 데이터까지 담아 반환해줌

    @Value("#{target.username + ' ' + target.age + ' ' + target.team.name}") //이렇게 SpEL문법을 사용하면, DB에서 엔티티 필드를 다 조회해온 다음에 계산한다! 따라서 JPQL SELECT 절 최적화가 안된다.
    String getUsername(); //인터페이스 기반 Open Projections

//    String getUsername(); //인터페이스 기반 Closed Projections
}
