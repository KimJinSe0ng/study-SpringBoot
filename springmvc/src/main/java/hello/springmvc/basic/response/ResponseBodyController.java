package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Controller 대신에 @RestController 애노테이션을 사용하면, 해당 컨트롤러에 모두
 * @ResponseBody 가 적용되는 효과가 있다. 따라서 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 데이터를 입력한다.
 * 이름 그대로 Rest API(HTTP API)를 만들 때 사용하는 컨트롤러이다.
 * 참고로 @ResponseBody 는 클래스 레벨에 두면 전체 메서드에 적용되는데, @RestController
 * 에노테이션 안에 @ResponseBody 가 적용되어 있다.
 */
@Slf4j
//@Controller
@RestController //@ResponseBody + @Controller
public class ResponseBodyController {

    //문자처리
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * @return
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //@ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    //JSON 처리
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(25);
        return new ResponseEntity<>(helloData, HttpStatus.OK); //상태 코드 변경 가능
    }

    //API 만들 때는 이런 스타일 많이 씀
    @ResponseStatus(HttpStatus.OK) //2.이렇게 상태 코드 변경 가능
//    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userB");
        helloData.setAge(30);
        return helloData; //1.상태 코드 변경 불가
    }
}