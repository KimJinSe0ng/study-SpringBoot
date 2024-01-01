package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    /**
     * 스프링 컨테이너가 리포지토리를 찾는데 등록한게 없다.
     * SpringDataJpaMemberRepository 하나가 있다. -> 인터페이스만 만들고 JpaRepository를 extends하면
     * 스프링 데이터 jpa가 인터페이스에 대한 구현체를 자기가 만들어서 스프링 빈에 등록해놓기 때문에 주입을 받을 수 있다.
     */

    //JPQL select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
