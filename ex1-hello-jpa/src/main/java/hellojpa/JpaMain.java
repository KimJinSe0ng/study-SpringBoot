package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

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

            //프록시 쿼리 확인
//            Member member = new Member();
//            member.setUsername("hello");
//
//            em.persist(member);
//            em.flush();
//            em.clear();
//
////            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.getReference(Member.class, member.getId()); //실제로 findMember를 실제 사용할 때 DB쿼리가 나감
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getTeam() = " + findMember.getTeam());

            //프록시 특징
//            Member member1 = new Member();
//            member1.setUsername("member1");
//            em.persist(member1);
//
//            em.flush();
//            em.clear();
//
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass()); //Proxy
//
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass()); //Member
//
//            System.out.println("refMember == findMember: " + (refMember == findMember)); //JPA는 어떻게든 이걸 맞춘다.

            //영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass()); //Proxy

//            em.detach(refMember); //could not initialize proxy 에러 발생
//
            refMember.getUsername(); //하면 프록시 초기화 됨
            Hibernate.initialize(refMember); //하면 프록시 강제 초기화
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); //프록시 인스턴스 초기화 여부 확인

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //이게 되게 중요함, EntityManager가 결국 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 사용하고 나면 닫아줘야 함
        }
        emf.close();
    }
}