package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) { //프론트 컨트롤러에 다 처리하고 맵에다가 요청 파라미터 정보를 다 넣어서 여기에 넘겨줄꺼고, 여기선 단순히 쓰기만 하면 됨
        String username = paramMap.get("username"); //map에서 필요한 요청 파라미터를 조회하면 된다. mv.getModel().put("member", member);
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member); //모델은 단순한 map이므로 모델에 뷰에서 필요한 member 객체를 담고 반환한다.
        return mv;
    }
}
