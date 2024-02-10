package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age); //도메인 특화된 조회

//    @Query(name = "Member.findByUsername") //없어도 잘 동작함 -> <Member, Long>의 Member 엔티티에 점을 찍고 findByUsername 메서드 명으로 네임드 쿼리를 찾아서 실행하고, 없으면 메서드 쿼리로 이름 생성하는 방식으로 함
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age") //이름이 없는 네임드 쿼리이기 때문에 실행 시점에 SQL 파싱하면서 에러를 잡아 줌
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t") //DTO는 new로 해서 해야 함
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUsername(String username); //컬렉션

    Member findMemberByUsername(String username); //단건

    Optional<Member> findOptionalByUsername(String name); //단건 Optional

//    Page<Member> findByAge(int age, Pageable pageable); //Pageable 현재 내가 1페이지, 2페이지...
//    Slice<Member> findByAge(int age, Pageable pageable); //Pageable 현재 내가 1페이지, 2페이지...

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m") //쿼리가 복잡하면 성능 테스트 후 카운트 쿼리 분리해야 함
    Page<Member> findByAge(int age, Pageable pageable); //Pageable 현재 내가 1페이지, 2페이지...
}
