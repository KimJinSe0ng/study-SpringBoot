package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig { //구성정보

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository() 이렇게 2번 호출이 되는데 싱글톤이 깨지는 것처럼 보인다. 깨질까? 안 깨질까?

    //예측
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.memberRepository
    //call AppConfig.orderService
    //call AppConfig.memberRepository

    //실제 : 스프링이 어떻게든 싱글톤으로 해준다.
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.orderService
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() { //역할
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository(); //구현
    }

    @Bean
    public OrderService orderService() { //역할
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy()); //구현
    }

    @Bean
    public DiscountPolicy discountPolicy() { //역할
//        return new FixDiscountPolicy(); //구현
        return new RateDiscountPolicy();
    }
}
