package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return mav;
    }

//    @ResponseBody //이거를 쓰면 리스폰스 바디에 직접하므로 뷰를 사용하려면 이렇게 하면 안 됨, @RestController도 마찬가지이다.
    @RequestMapping("/response-view-v2") //이렇게 사용하자.
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello"; //@Controller 이면서 String으로 반환하면 return에 있는 " ~ "가 뷰의 이름이 된다.
    }

    @RequestMapping("/response/hello") //권장하지 않는 방식
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!!");
    }

}