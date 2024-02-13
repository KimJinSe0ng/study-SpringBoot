package study.querydsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import study.querydsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, QuerydslPredicateExecutor { //MemberRepositoryCustom 를 상속받기 때문에 search()를 외부에서 호출 가능

    //select m from Member m where m.username = ?
    List<Member> findByUsername(String username);
}
