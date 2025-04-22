// src/main/java/com/project/java/filter/AuthFilter.java
package com.project.java.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
    	
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        System.out.println("필터");
     // 정적 리소스, 로그인/회원가입 페이지, 공개 페이지는 인증 없이 접근 가능
        boolean isResourceRequest = requestURI.startsWith(contextPath + "/asset/");
        boolean isAuthRequest = requestURI.startsWith(contextPath + "/user/login") ||
                                requestURI.startsWith(contextPath + "/user/register") ||
                                requestURI.startsWith(contextPath + "/user/google-login") ||
                                requestURI.startsWith(contextPath + "/user/verify-email") ||
                                requestURI.startsWith(contextPath + "/user/forgot-password") ||
                                requestURI.startsWith(contextPath + "/user/reset-password") ||
                                requestURI.startsWith(contextPath + "/user/mypage/mypage.do");
        System.out.println("필터 통과");
    // 인덱스페이지 추가
        boolean isPublicPage = requestURI.equals(contextPath + "/") ||
        						requestURI.equals(contextPath + "/index.jsp") ||
        						requestURI.equals(contextPath + "/index.do");
        
        boolean isLoggedIn = (session != null && (session.getAttribute("user") != null || session.getAttribute("userSeq") != null));

        
        if (isResourceRequest || isAuthRequest || isPublicPage || isLoggedIn) {
            chain.doFilter(request, response);
        } else {
            // 로그인 페이지로 리다이렉트하기 전에 요청 URL 저장
            session = httpRequest.getSession();
            session.setAttribute("redirectURL", requestURI);
            httpResponse.sendRedirect(contextPath + "/user/login");
            
        }
    }
    
    @Override
    public void destroy() {}
}
