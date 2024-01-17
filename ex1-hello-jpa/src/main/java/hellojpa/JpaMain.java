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
            //1. 회원 등록(저장)
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

            //2. 회원 수정
            /**
             * 업데이트를 하고 다시 persist를 해야하나? No!
             * JPA를 통해서 Entity를 가져오면 이게 변경이 되었는지 안 되었는지 트랜잭션 commit 하는 시점에 확인을 하고,
             * 바뀐걸 감지하면 Update 쿼리 날리고 트랜잭션 commit 함
             */
//            Member findMember = em.find(Member.class, 1L); //3. 회원 조회: find(엔티티 클래스, PK)
//            findMember.setName("HelloJPA");

            //3. JPQL로 전체 회원 조회 : 범위 질의는 JPQL로 한다.
            /**
             * JPA입장에선, 코드를 짤 때 테이블 대상으로 짜지 않고, Member 객체를 대상으로 쿼리함. 대상이 table이 아니고 객체를 대상으로 쿼리함
             * Member 객체를 다 가져와라.
             * select하고 필드를 다 나열했어. m을 select 했는데, 이 m은 JPQL은 멤버 엔티티를 선택한 것이다.
             */
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(0)//0번부터 1개가져와
                    .setMaxResults(1)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
            }
            tx.commit(); //여기서 문제가 생기면 close() 두 개가 호출이 되지 않아 좋지 않은 코드임 -> try-catch
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //이게 되게 중요함, EntityManager가 결국 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 사용하고 나면 닫아줘야 함
        }
        emf.close();
    }
}