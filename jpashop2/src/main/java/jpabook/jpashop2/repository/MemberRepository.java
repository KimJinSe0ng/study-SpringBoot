package jpabook.jpashop2.repository;

import jpabook.jpashop2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> { //<타입, PK타입>

    //select m from Member m where m.name = ?로 짜버림 -> 메서드 네임(ByName)을 보고 JPQL을 만든다. Nameasdfka 이런식이면 안 됨
    List<Member> findByName(String name);
}
