package com.project.java.user;

import com.project.java.user.auth.EmailVerificationService;
import com.project.java.user.model.UserDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;

@WebServlet("/user/verify-email")
public class EmailVerification extends HttpServlet {

    
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("이메일 인증");
        String email = request.getParameter("email");
        String action = request.getParameter("action");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();

        if ("send".equals(action)) {
            // 이메일 주소가 이미 존재하는지 확인
            if (userDAO.isEmailExists(email)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "이미 가입된 이메일 주소입니다.");
            } else {
                // 인증 코드 생성 및 이메일 전송
                String code = EmailVerificationService.generateVerificationCode();
                // 만료 시간 설정 (30분 후)
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 30);
                Timestamp expireTime = new Timestamp(calendar.getTimeInMillis());

                boolean sent = EmailVerificationService.sendVerificationEmail(email, code);
                
                if (sent) {
                    // 인증 코드 저장
                    userDAO.saveVerificationCode(email, code, expireTime);
                    jsonResponse.put("success", true);
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "이메일 전송에 실패했습니다.");
                }
            }
        } else if ("verify".equals(action)) {
        	// 인증 코드 확인
            String code = request.getParameter("code");
            boolean verified = userDAO.verifyCode(email, code);
            
            if (verified) {
                HttpSession session = request.getSession();
                session.setAttribute("verified_email", email);
                System.out.println("Email verified: " + email); // 로깅 추가
                jsonResponse.put("success", true);
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "인증 코드가 일치하지 않거나 만료되었습니다.");
            }
        }
        
        response.getWriter().write(jsonResponse.toString());
    }
}
