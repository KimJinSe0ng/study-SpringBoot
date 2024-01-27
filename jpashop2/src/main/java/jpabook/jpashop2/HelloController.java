package jpabook.jpashop2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) { //Model에 데이터를 실어 View로 넘김
        model.addAttribute("data", "hello!!"); //data란 이름에 hello!! 값을 넘김
        return "hello"; //화면 이름 resources/templates/ + return 다음 이름.html으로 자동으로 붙음
    }
}
