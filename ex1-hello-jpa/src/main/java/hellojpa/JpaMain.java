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

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 연관관계 저장
            Member member = new Member();
            member.setUsername("member1");
//            member.setTeamId(team.getId()); // 이 부분이 애매하다. 외래키 식별자를 직접 다룸, 객체지향적이지 않음
            member.setTeam(team); // JPA가 알아서 팀에서 PK값을 꺼내서 FK값에 Insert할 때 FK값을 사용한다, 단방향 연관관계 설정, 참조 저장
            em.persist(member);

            em.flush();
            em.clear();

            // 참조를 사용해서 연관관계 조회
            Member findMember = em.find(Member.class, member.getId()); // 조회도 문제가 된다. 연관관계가 없어서 객체지향적이지 않음
//            Long findTeamid = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamid);
            Team findTeam = findMember.getTeam(); // 참조를 사용해서 연관관계 조회
            System.out.println("findTeam = " + findTeam.getName());

            // 연관관계 수정
            Team newTeam = em.find(Team.class, 100L); // DB에 100번 팀이 있다고 가정하고, 팀을 바꿔주면 FK가 업데이트 된다.
            findMember.setTeam(newTeam);

            tx.commit(); //여기서 문제가 생기면 close() 두 개가 호출이 되지 않아 좋지 않은 코드임 -> try-catch
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); //이게 되게 중요함, EntityManager가 결국 내부적으로 데이터베이스 커넥션을 물고 동작하기 때문에 사용하고 나면 닫아줘야 함
        }
        emf.close();
    }
}