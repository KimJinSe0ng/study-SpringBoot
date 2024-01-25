package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //EntityManagerFactory는 애플리케이션 로딩 시점에 딱 하나만 만들어둬야 함
        //persistence.xml의 <persistence-unit name="hello">를 넣어야 한다.
        //JPA는 항상 엔티티 팩토리를 만들어야 한다. → 데이터 베이스 하나씩 묶여서 돌아간다.

        EntityManager em = emf.createEntityManager(); //실제 DB에 저장하려거나 하는 트랜잭션 단위
        //예를 들어, 고객이 장바구니에 상품을 담아 놓는것을 할 때마다(DB 커넥션을 얻고 쿼리 날리고 종료되는 일관적인 단위를 할 때마다 EntityManager를 꼭 만들어줘야 함)
        //EntityManager를 자바 컬렉션처럼 이해하면 됨. 내 객체를 대신 저장해주는 역할

        EntityTransaction tx = em.getTransaction(); //JPA의 모든 데이터 변경은 트랜잭션 안에서 실행

        tx.begin();
        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team teamB = new Team();
            team.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId()); //즉시로딩의 경우, Member와 Team을 조인해서 조회해옴. 지연로딩의 경우, 실제 team이 사용될 때 쿼리 사용(실제 엔티티)
//            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
            //SQL: select * from Member; 로 일단 나가는데, EAGER이기 때문에 List<Member>에 모든 값이 다 들어가있어야 해
            //SQL: select * from Team where TEAM_ID = xxx
            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList(); //페치 조인으로 N+1문제 해결 가능
            //N+1문제 해결 방법
            //일단은 모든 연관관계를 지연로딩으로 깔고
            //1. JPQL에서 패치 조인을 배움 -> 동적으로 가져와서 써줌 (대부분 해결)
            //2. 엔티티 그래프 라는 어노테이션
            //3. 배치 사이즈

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //이게 되게 중요함, EntityManager가 결국 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 사용하고 나면 닫아줘야 함
        }
        emf.close();
    }
}