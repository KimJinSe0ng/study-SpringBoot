package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        MemberService memberService = new MemberServiceImpl();
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class); //AppConfig에 있는 환경 설정 정보를 갖고 스프링 컨테이너에 등록하여 관리해줌
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        //첫번째 인자: 메서드 이름(기본적으로 메서드 이름으로 빈이 등록됨), 두번째 인자: 타입(반환타입)

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
