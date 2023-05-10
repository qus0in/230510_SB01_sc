package io.playdata.sc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping
    public String home(Model model, HttpSession session) {
        // 이 경로로 접속하게 되면 session을 사용.
        // (삼항연산자 in 자바) 조건문 ? 참일 때 : 거짓일 때
        int count = session.getAttribute("count") == null ? 0 : (int) session.getAttribute("count");
        count++;
        session.setAttribute("count", count);
        model.addAttribute("message", "이번까지 "+ count + "회 방문하셨습니다 (세션)");
        return "home"; // localhost:8080 / src/main/resources/templates/home.html
    }

    @GetMapping("/cookie")
    public String home2(Model model, HttpServletResponse response,
                        @CookieValue(value = "count", defaultValue = "0") Integer count) {
        // 이 경로로 접속하게 되면 cookie를 사용.
        count++; // count를 1 증가
        // 쿠키 타입의 객체를 만들 거고, 이름(키)는 count고 그 값은 count 패러미터를 문자열로 바꾼 값
        Cookie cookie = new Cookie("count", count.toString());
        cookie.setMaxAge(60 * 60 * 24 * 365); // 만료기간을 1초 단위로 설정 -> 1년
        // 쿠키는 우리가 직접 지우거나, 만료기간이 다 되면 삭제됨.
        response.addCookie(cookie);
        model.addAttribute("message", "이번까지 "+ count + "회 방문하셨습니다 (쿠키)");
        return "home"; // localhost:8080/cookie / src/main/resources/templates/home.html
    }
}
