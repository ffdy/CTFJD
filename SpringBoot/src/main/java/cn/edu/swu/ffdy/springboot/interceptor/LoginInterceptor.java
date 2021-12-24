package cn.edu.swu.ffdy.springboot.interceptor;

import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute(SessionContents.LOGIN_STATUS);

        if(isLogin == null || isLogin.equals(Boolean.FALSE)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println(request.getRequestURL());
            return false;
        }
        return true;
    }
}
