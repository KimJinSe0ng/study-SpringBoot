package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }



        Map<String, String> paraMap = createParamMap(request);

//        MyView view = controller.process(request, response);
        ModelView mv = controller.process(paraMap); //파라미터 완료

        //뷰로 전달, new-form
        String viewName = mv.getViewName(); //논리이름 new-form만 얻는 것이기 때문에 물리적인 view 객체를 만들기 위해 뷰 리졸버 생성(실제 view를 찾아주는 역할)
        MyView view = viewResolver(viewName); //실제 물리이름을 포함한 뷰로 반환됨

//        view.render(request, response);
        view.render(mv.getModel(), request, response); //렌더를 호출하며 모델을 넘김
    }

    private static MyView viewResolver(String viewName) { //view의 논리 이름을 가지고 실제 물리 이름을 만들면서 MyView를 반환하는 메소드
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
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