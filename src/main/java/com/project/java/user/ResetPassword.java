package com.project.java.user;

import com.project.java.user.model.UserDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/user/reset-password")
public class ResetPassword extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String email = request.getParameter("email");
        
        // 토큰 유효성 검사
        if (token == null || email == null || !userDAO.isValidPasswordResetToken(email, token)) {
            response.sendRedirect(request.getContextPath() + "/user/forgot-password?error=invalid");
            return;
        }
        
        // 비밀번호 재설정 페이지 표시
        request.getRequestDispatcher("/WEB-INF/views/user/auth/reset_password.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // 입력값 유효성 검사
        if (token == null || email == null || password == null || confirmPassword == null) {
            request.setAttribute("errorMessage", "모든 필드를 입력해주세요.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/reset_password.jsp").forward(request, response);
            return;
        }
        
        // 비밀번호 일치 확인
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/reset_password.jsp").forward(request, response);
            return;
        }
        
        // 비밀번호 길이 확인
        if (password.length() < 8) {
            request.setAttribute("errorMessage", "비밀번호는 8자 이상이어야 합니다.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/reset_password.jsp").forward(request, response);
            return;
        }
        
        // 토큰 유효성 검사
        if (!userDAO.isValidPasswordResetToken(email, token)) {
            response.sendRedirect(request.getContextPath() + "/user/forgot-password?error=invalid");
            return;
        }
        
        // 비밀번호 암호화
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        // 비밀번호 업데이트
        boolean updated = userDAO.updatePassword(email, hashedPassword);
        
        if (updated) {
            // 토큰 삭제
            userDAO.deletePasswordResetToken(email);
            
            // 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/user/login?reset=success");
        } else {
            request.setAttribute("errorMessage", "비밀번호 업데이트에 실패했습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/reset_password.jsp").forward(request, response);
        }
    }
}
