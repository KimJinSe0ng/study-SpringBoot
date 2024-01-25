package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "strret", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();
            System.out.println("=========== START ==============");
            Member findMember = em.find(Member.class, member.getId());

            //값 타입 수정
            //homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity"); //사이드 이펙트 초래
//            Address a = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getStreet())); //값 타입, 업데이트를 하는게 아니라 통째로 갈아 끼워 넣어야 한다.
//
//            //치킨 -> 한식 : 값 타입, 업데이트를 하는게 아니라 통째로 갈아 끼워 넣어야 한다.
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            //old1 -> new1
//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000")); //기본적으로 컬렉션은 대상을 찾을때 equals, hashCode를 사용한다.
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000")); //delete 1 , insert 2가 발생하는데, 데이터를 아예 새로 갈아 끼워넣는 것이다.

            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //이게 되게 중요함, EntityManager가 결국 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 사용하고 나면 닫아줘야 함
        }
        emf.close();
    }
}