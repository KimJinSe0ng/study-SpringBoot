package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            //기본 문법
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class); //TypedQuery:반환 타입이 명확할 때 사용
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            Query query3 = em.createQuery("select m.username, m.age from Member m"); //Query:반환 타입이 명확하지 않을 때 사용
//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();

            //프로젝션
//            List<Member> result = em.createQuery("select m from Member m", Member.class) //엔티티 프로젝션
//            List<Team> result = em.createQuery("select m.team from Member m", Team.class) //엔티티 프로젝션
//            Member findMember = result.get(0); //수정 가능
//            findMember.setAge(20);

//            em.createQuery("select o.address from Order o", Address.class) //임베디드 타입 프로젝션: 임베디드의 엔티티에서 시작해야 함
//                    .getResultList();

//            em.createQuery("select distinct m.username, m.age from Member m") //스칼라 타입 프로젝션
//                    .getResultList();

            //프로젝션 - 여러 값 조회
//            List resultList = em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            System.out.println("username = " + result[0]);
//            System.out.println("age = " + result[1]);

            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());

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
