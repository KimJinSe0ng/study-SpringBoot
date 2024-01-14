package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE) //클래스 레벨(타입)에 붙느냐, 필드에 붙느냐 지정
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
