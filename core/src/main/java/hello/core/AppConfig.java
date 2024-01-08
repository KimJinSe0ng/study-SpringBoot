package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig { //구성정보

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemoryMemberRepository memberRepository() { //역할
        return new MemoryMemberRepository(); //구현
    }

    public OrderService orderService() { //역할
        return new OrderServiceImpl(memberRepository(), discountPolicy()); //구현
    }

    public DiscountPolicy discountPolicy() { //역할
//        return new FixDiscountPolicy(); //구현
        return new RateDiscountPolicy();
    }
}
