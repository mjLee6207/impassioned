package egovframework.example.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String fullUrl = uri + (query != null ? "?" + query : "");

        log.info("📌 요청 URI: {}", uri);
        log.info("📌 전체 URL (with query): {}", fullUrl);

        if (session == null || session.getAttribute("loginUser") == null) {
            log.info("🚫 비로그인 사용자 → 로그인 페이지로 리다이렉트");

            String loginUrl = request.getContextPath() + "/member/login.do?redirect=" +
                              java.net.URLEncoder.encode(fullUrl, "UTF-8");

            response.sendRedirect(loginUrl);
            return false;
        }

        log.info("✅ 로그인 사용자 → 요청 통과");
        return true;
    }
}
