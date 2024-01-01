package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name); //Optional은 findByName으로 가져오는데 null이 반환될 수 있는데, null을 그대로 반환하는 것보다 Optional로 감싸서 반환하는 방법 선호

    List<Member> findAll();

}
