package edu.guopengl;

import edu.guopengl.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = (String) request.getSession().getAttribute("token");
        if(token != null) {
            Claims claims = JWTUtil.checkJWT(token);
            if(claims != null){
                return true;
            }
        }

//        response.setContentType("text/html; charset=utf-8");
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter pw = response.getWriter();
//        pw.print("<script>alert('请先登录!!!');window.location.href='/index.html';</script>");
        response.sendRedirect("index.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
