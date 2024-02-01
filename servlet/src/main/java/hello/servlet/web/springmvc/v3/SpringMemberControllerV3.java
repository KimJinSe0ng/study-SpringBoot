package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    //    @RequestMapping(value = "/new-form", method = RequestMethod.GET) // /springmvc/v2/members 에 /new-form 이 더해짐
    // GET, POST 방식 상관 없이 받아줌
    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
//        return new ModelAndView("new-form");
        // ModelAndView로 반환해도 되지만 String으로 해도 View이름으로 알고 process가 진행됨
    }

    //    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save") //HTTP 서블릿 리퀘스트, 리스폰스도 받을 수 있지만 다음과 같이 파라미터를 직접 받을 수 있고, 모델도 받을 수 있다.
    public String save(@RequestParam("username") String username, @RequestParam("age") int age, Model model) {
        // 1.username, age 요청 파라미터 받고
//        String username = request.getParameter("username"); //파라미터에서 받으므로 삭제
//        int age = Integer.parseInt(request.getParameter("age"));

        // 2.비지니스 로직하고
        Member member = new Member(username, age);
        memberRepository.save(member);

//        ModelAndView mv = new ModelAndView("save-result");
//        mv.addObject("member", member);
        // 3.파라미터로 넘어온 모델에 담고
        model.addAttribute("member", member);
        // 4.뷰에 이름 반환
        return "save-result";
    }

    //    @RequestMapping(method = RequestMethod.GET)// 아무것도 쓰지 않으면 /springmvc/v2/members 가 불러짐
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
//        ModelAndView mv = new ModelAndView("members");
//        mv.addObject("members", members);
        return "members";
    }

}