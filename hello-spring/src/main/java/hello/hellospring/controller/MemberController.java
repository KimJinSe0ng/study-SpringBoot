package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;
    //MemberService를 여러개 인스턴스를 생성할 필요 없다. -> 스프링 컨테이너에 등록 -> 딱 하나만 등록된다.

    @Autowired //MemberController가 생성될 때 스프링 빈에 등록되어 있는 서비스 객체를 갖다 넣어 준다. -> 의존관계 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
