package com.project.java.user;

import com.project.java.user.auth.EmailVerificationService;
import com.project.java.user.model.UserDAO;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/forgot-password")
public class ForgotPassword extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("비밀번호찾기");
        // 비밀번호 찾기 페이지 표시
        request.getRequestDispatcher("/WEB-INF/views/user/auth/forgot_password.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        
        // 이메일 유효성 검사
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "이메일을 입력해주세요.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/forgot_password.jsp").forward(request, response);
            return;
        }
        
        // 이메일 존재 여부 확인
        if (!userDAO.isEmailExists(email)) {
            request.setAttribute("errorMessage", "등록되지 않은 이메일입니다.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/forgot_password.jsp").forward(request, response);
            return;
        }
        
        // 비밀번호 재설정 토큰 생성
        String token = UUID.randomUUID().toString();
        
        // 토큰 만료 시간 설정 (24시간)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        Timestamp expireTime = new Timestamp(calendar.getTimeInMillis());
        
        // 토큰 저장
        userDAO.savePasswordResetToken(email, token, expireTime);
        
        // 비밀번호 재설정 링크 생성
        String resetLink = request.getScheme() + "://" + request.getServerName() + ":" + 
                          request.getServerPort() + request.getContextPath() + 
                          "/user/reset-password?token=" + token + "&email=" + email;
        
        // 이메일 발송
        String emailSubject = "개발자 로드맵 - 비밀번호 재설정";
        String emailBody = "안녕하세요,\n\n" +
                          "개발자 로드맵 비밀번호 재설정을 위한 링크입니다: " + resetLink + 
                          "\n\n이 링크는 24시간 동안 유효합니다.";
        
        boolean sent = EmailVerificationService.sendEmail(email, emailSubject, emailBody);
        
        if (sent) {
            request.setAttribute("successMessage", "비밀번호 재설정 링크가 이메일로 발송되었습니다. 이메일을 확인해주세요.");
        } else {
            request.setAttribute("errorMessage", "이메일 발송에 실패했습니다. 다시 시도해주세요.");
        }
        
        request.getRequestDispatcher("/WEB-INF/views/user/auth/forgot_password.jsp").forward(request, response);
    }
}
