package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

//            String query = "select m.team.name from Member m"; //상태필드(name)라 경로 겸색 더 이상 못함
//            String query = "select m.team from Member m"; //단일 값 연관 경로: 묵시적 내부 조인(inner join) 발생, 탐색 가능
//
//            List<Team> result = em.createQuery(query, Team.class) //Team -> 조인이 엄청 발생: 조심해야겠구나 -> 묵시적 내부 조인 발생하지 않게 짜야 함
//                    .getResultList();
//
//            for (Team s : result) {
//                System.out.println("s = " + s);
//            }

//            String query = "select t.members.size from Team t"; //컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색X
            //컬렉션 자체를 가리키는 것이기 때문에 필드를 찍을 수가 없다. 따라서 탐색이 불가능함
            String query = "select m.username from Team t join t.members m"; //해결방법: 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

            Integer result = em.createQuery(query, Integer.class)
                    .getSingleResult();

            System.out.println("result = " + result);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
