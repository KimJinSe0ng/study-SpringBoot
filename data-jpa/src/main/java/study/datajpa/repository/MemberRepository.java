package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age); //도메인 특화된 조회

//    @Query(name = "Member.findByUsername") //없어도 잘 동작함 -> <Member, Long>의 Member 엔티티에 점을 찍고 findByUsername 메서드 명으로 네임드 쿼리를 찾아서 실행하고, 없으면 메서드 쿼리로 이름 생성하는 방식으로 함
    List<Member> findByUsername(@Param("username") String username);
}
