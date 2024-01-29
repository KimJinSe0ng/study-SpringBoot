package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK); //200보다 안에 들어있는 값 쓰는게 좋음

        //[response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        //[Header 편의 메서드]
//        content(response);

        //[Cookie 편의 메서드]
//        cookie(response);

        //[Redirect 편의 메서드]
        redirect(response);

        //[message body]
        PrintWriter writer = response.getWriter(); //PrintWriter는 자바에서 텍스트 데이터를 출력하는 데 사용되는 클래스
        writer.println("ok"); //서버가 클라이언트로 텍스트 데이터를 전송
    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        // 위의 두 줄을 아래 세 줄로 대체함
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //이 쿠키는 600초 동안 유효하다.
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302 //상태 코드는 302로 만들거고
        //Location: /basic/hello-form.html //로케이션 헤더를 여기로 보낼거임

//        response.setStatus(HttpServletResponse.SC_FOUND); //302
//        response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html"); //위의 두 줄을 한 줄로 대체함
    }
}
