package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paraMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>(); //v4 Model 추가

//        ModelView mv = controller.process(paraMap); //v3
//        String viewName = mv.getViewName(); //v3
        String viewName = controller.process(paraMap,model); //v4 기존에 모델뷰에서 받던 건 필요 없음

        MyView view = viewResolver(viewName);
//        view.render(mv.getModel(), request, response); //v3
        view.render(model, request, response); //v4 기존에 모델뷰에서 모델을 꺼냈었는데, 모델을 직접 제공
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