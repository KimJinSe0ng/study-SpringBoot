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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            member1.setAge(0);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            member1.setAge(0);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            member1.setAge(0);
            em.persist(member3);

//            em.flush();
//            em.clear();

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
//            String query = "select m.username from Team t join t.members m"; //해결방법: 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

//            String query = "select m from Member m"; //단순 조회
//            String query = "select m from Member m join fetch m.team"; //페치 조인: 페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩X
//            String query = "select t from Team t join fetch t.members"; //컬렉션 페치 조인: 페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
//            String query = "select distinct t from Team t join fetch t.members"; //페치조인과 DISTINCT
//            String query = "select t from Team t join t.members m"; //페치 조인과 일반 조인의 차이: 일반조인 실행시 연관된 엔티티를 함께 조회하지 않음

//            String query = "select t from Team t join fetch t.members as m join fetch m.team"; //페치 조인의 특징과 한계: 페치 조인을 몇 단계 거쳐 가져올 때만 씀(주의)
//            String query = "select m from Member m join fetch m.team t"; //페이징 해결 방법1
//            String query = "select t from Team t"; //페이징 해결 방법2

//            String query = "select m from Member m where m = :member"; //엔티티 직접 사용 - 기본 키 값
//            String query = "select m from Member m where m.team = :team"; //엔티티 직접 사용 - 외래 키 값

//            List<Team> result = em.createQuery(query, Team.class) //페이징
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();

//            Member findMember = em.createQuery(query, Member.class)
//                    .setParameter("member", member1)
//                    .getSingleResult();

//            List<Member> members = em.createQuery(query, Member.class)
//                    .setParameter("team", teamA)
//                    .getResultList();

//            for (Member member : members) {
//                System.out.println("member = " + member);
//            }

//            for (Member member : result) { //페치 조인: 페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩X
//                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
//                //회원1, 팀A(SQL)
//                //회원2, 팀A(1차캐시)
//                //회원3, 팀B(SQL)
//
//                //최악의 경우, 회원이 100명이 있고 팀 소속이 다 다르다면? 직원 100명 -> 쿼리 100번 나감 -> N + 1(1은 회원을 가져오기 위한 쿼리, N은 루프도는 횟수)
//            }

//            System.out.println("result = " + result.size());
//
//            for (Team team : result) { //컬렉션 페치 조인: 페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
//                System.out.println("team = " + team.getName() + "|members = " + team.getMembers().size());
//                for (Member member : team.getMembers()) {
//                    System.out.println("-> member = " + member);
//                }
//            }

//            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class) //Named 쿼리
//                    .setParameter("username", "회원1")
//                    .getResultList();
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }

            //셋 다 0살로 insert되어 있고,
            //flush() 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20") //벌크 연산, 이 시점에 현재 영속성 컨텍스트에는 셋 다 0살이지만, DB에는 20살로 업데이트 되어 있음
                    .executeUpdate(); //DB에만 반영이 되어 있기 때문에 영속성 컨텍스트도 업데이트 해주야 함
            em.clear(); //영속성 컨텍스트 초기화
            Member findMember = em.find(Member.class, member1.getId()); //DB에서 새로 가져옴
            System.out.println("findMember = " + findMember.getAge());
            System.out.println("resultCount = " + resultCount);

            System.out.println("member1.getAge() = " + member1.getAge()); //0살
            System.out.println("member2.getAge() = " + member2.getAge()); //0살
            System.out.println("member3.getAge() = " + member3.getAge()); //0살

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
