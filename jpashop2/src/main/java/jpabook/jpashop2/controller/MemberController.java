package jpabook.jpashop2.controller;

import jakarta.validation.Valid;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        //왜 Member를 넣는게 아니고 MemberForm를 넣는가? -> 멤버는 id, address, orders도 있고, 두 개가 안 맞는다.
        //컨트롤러에서 화면에서 넘어올 때 검증이랑 실제 도메인이 원하는 검증이 다를 수 있다.
        //엔티티를 폼 데이터에 쓰게 되면 억지로 맞춰야 하기 때문에 화면에 fit한 폼 데이터를 만들고 데이터를 받는게 낫다.
        //그래서 폼으로 받고 컨트롤러 같은 곳에서 한 번 중간에 정제를 하고 필요한 데이터만 채워 넘기는 걸 권장한다.

        if (result.hasErrors()) {
            return "members/createMemberForm"; //BindingResult를 members/createMemberForm 화면까지 긁어 감 -> 에러 보여줌
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
}
