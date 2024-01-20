package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

//            //비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            //영속
//            em.persist(member);

//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//            em.persist(member1);
//            em.persist(member2);
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZZ");

//            em.persist(member); //호출해야 하는거 아니야? X

            System.out.println("============");

            tx.commit(); //여기서 문제가 생기면 close() 두 개가 호출이 되지 않아 좋지 않은 코드임 -> try-catch
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //이게 되게 중요함, EntityManager가 결국 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 사용하고 나면 닫아줘야 함
        }
        emf.close();
    }
}