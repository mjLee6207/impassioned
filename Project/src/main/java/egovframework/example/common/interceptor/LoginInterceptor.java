package egovframework.example.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
	                         HttpServletResponse response,
	                         Object handler) throws Exception {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("loginUser") == null) {
	        // 로그인되지 않은 사용자

	        String uri = request.getRequestURI();
	        String query = request.getQueryString();
	        String fullUrl = uri + (query != null ? "?" + query : "");

	        // ✅ URL 파라미터로 redirect 넘기기 (세션 사용 안함)
	        String loginUrl = request.getContextPath() + "/member/login.do?redirect=" + java.net.URLEncoder.encode(fullUrl, "UTF-8");

	        response.sendRedirect(loginUrl);
	        return false;
	    }
        return true; // 로그인 되어 있으면 통과
    }
}