package jpabook.jpashop2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext //스프링이 EntityManagerFactory 부터 해서 모두 주입해줌
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
        //커맨드랑 쿼리를 분리해라: 저장하고 나면 이건 사이드 이펙트를 일으키는 커맨드성이기 때문에 리턴값을 거의 만들지 않지만 ID 정도만 있으면 다음에 다시 조회할 수 있으니 이렇게 함.
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
