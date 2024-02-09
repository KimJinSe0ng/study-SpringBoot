package jpabook.jpashop2.domain.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.repository.MemberRepositoryOld;
import jpabook.jpashop2.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //JUnit 실행할 때 Spring이랑 엮어서 실행하려면 필요
@SpringBootTest //스프링 부트를 올려서 테스트 할 때 필요, @RunWith(SpringRunner.class), @SpringBootTest가 있어야 스프링 부트를 실제 올려서 테스트 함
@Transactional //기본적으로 커밋하지 않고 롤백 함
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false) //롤백 false하면 쿼리 볼 수 있음
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예약() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 함

        //then
        fail("예외가 발생해야 한다.");
    }

}