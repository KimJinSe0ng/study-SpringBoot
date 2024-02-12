package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

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

    @Modifying(clearAutomatically = true) //@Modifying가 있어야 executeUpdate 해줌, clearAutomatically = true는 쿼리가 나가고 em.clear()기능 자동으로 해줌
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) //JPQL 말고, member도 조회하며 team도 조회하고 싶어 -> EntityGraph는 내부적으로 페치 조인 실행
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph(); //JPQL 쓰면서 team도 페치 조인 하고 싶어

    @EntityGraph(attributePaths = {"team"})
//    @EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) //readOnly가 true면 스냅샷을 만들지 않음, 변경 감지를 하지 않음
    Member findReadOnlyByUsername(String username);

    //lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
