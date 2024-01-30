package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3); //ControllerV3 을 처리할 수 있는 어댑터, 다른게 들어오면 다 false
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler; //캐스팅 해서 씀

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        return mv; //어댑터는 호출한 컨트롤러의 반환 타입을 맞추어 반환해야 함
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        //paraMap을 넘겨줘야 함
        Map<String, String> paraMap = new HashMap<>();
        //request의 모든 파라미터 이름을 가져오고 반복하면서 key의 변수명:paramName이 되고 request의 파라미터를 다 가져와서 Map에 넣음
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paraMap.put(paramName, request.getParameter(paramName)));
        return paraMap;
    }


}