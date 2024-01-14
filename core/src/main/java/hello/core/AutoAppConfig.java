package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( //@Component가 붙은 클래스를 자동으로 찾아 스프링 빈에 등록해 줌
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //그 중에서 뺄거 지정
        //Configuration.class를 빼주는 이유는 AppConfig, TestConfig 클래스를 등록하지 않기 위해서! -> 기존 예제 코드를 유지하기 위함
)
public class AutoAppConfig {

}
