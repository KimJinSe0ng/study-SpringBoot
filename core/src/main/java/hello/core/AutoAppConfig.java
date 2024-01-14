package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( //@Component가 붙은 클래스를 자동으로 찾아 스프링 빈에 등록해 줌
//        basePackages = "hello.core.member", //member만 컴포넌트 스캔 대상이 됨, 기본값은 @ComponentScan가 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //그 중에서 뺄거 지정
        //Configuration.class를 빼주는 이유는 AppConfig, TestConfig 클래스를 등록하지 않기 위해서! -> 기존 예제 코드를 유지하기 위함
)
public class AutoAppConfig {

}
