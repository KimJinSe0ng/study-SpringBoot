package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController //1-1.@Controller 하면 //2-1. @RestController 하면
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); //@Slf4j 하면 자동으로 해줌

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);
//        log.trace("trace log = " +  name); //이렇게 사용하면 안됨, +연산이 일어나며 메모리도 사용함(trace 로그를 쓰지도 않는데 사용함)

        log.trace("trace log = {}", name); //파라미터만 넘기므로 연산 안 함
//        log.trace("trace log = {}, {}", name, name1); //여러개 사용 가능
        log.debug("debug log = {}", name);
        log.info("info log = {}", name); //시간, 프로세스ID, [실행한 쓰레드], 컨트롤러 이름, 메시지 출력
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok"; //1-2.View 이름이 반환되는건데 //2-2. String이 반환 됨 -> HttpBody에 넣음
    }
}