// src/main/java/com/project/java/user/LoginServlet.java
package com.project.java.user;

import com.project.java.user.model.UserDAO;
import com.project.java.user.model.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/login")
public class Login extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	System.out.println("로그인 페이지");
        // 로그인 페이지로 포워딩
        //request.getRequestDispatcher("/WEB-INF/views/user/auth/login.jsp").forward(request, response);
		/* String googleClientId = System.getenv("GOOGLE_CLIENT_ID"); */
		/* request.setAttribute("googleClientId", googleClientId); */
    	request.getRequestDispatcher("/WEB-INF/views/user/auth/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // 입력값 검증
        if (email == null || password == null || 
            email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "이메일과 비밀번호를 입력해주세요.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/login.jsp").forward(request, response);
            return;
        }
        
        // 로그인 처리
        UserDTO user = userDAO.loginUser(email, password);
        if (user != null) {
            // 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userSeq", user.getUserSeq()); // userId에서 userSeq로 수정
            session.setAttribute("name", user.getName());
            System.out.println("userSeq: " + user.getUserSeq());
            
            // 요청된 페이지가 있으면 해당 페이지로, 없으면 메인 페이지로 리다이렉트
            String redirectURL = (String) session.getAttribute("redirectURL");
            if (redirectURL != null) {
                session.removeAttribute("redirectURL");
                response.sendRedirect(redirectURL);
            } else {
                response.sendRedirect(request.getContextPath() + "/index.do");//임시
            }
        } else {
            // 로그인 실패
            request.setAttribute("errorMessage", "이메일 또는 비밀번호가 올바르지 않습니다.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/login.jsp").forward(request, response);
        }
    }
}
