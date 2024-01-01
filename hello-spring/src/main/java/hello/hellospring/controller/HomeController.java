package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") //참고: 컨트롤러가 정적 파일보다 우선순위가 높기 때문에 "/"를 매핑하는 것이 없다면 resources/static/index.html이 호출된다.
    public String home() {
        return "home";
    }
}
