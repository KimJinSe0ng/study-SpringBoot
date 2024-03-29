package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String Hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string") //@ResponseBody 문자 반환
    @ResponseBody //http는 헤더부, 바디부가 있는데 바디부에 이 데이터 내용을 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //"hello String"
    }

    @GetMapping("hello-api") //@ResponseBody 객체 반환
    @ResponseBody//api 방식 : Json
    //ResponseBody가 있으면 HttpMessageConverter가 동작한다. 단순 문자면 StringConverter가 작동, 객체면 JsonConverter가 작동
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
