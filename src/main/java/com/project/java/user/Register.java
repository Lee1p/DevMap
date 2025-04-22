// src/main/java/com/project/java/user/RegisterServlet.java
package com.project.java.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.project.java.user.model.UserDAO;
import com.project.java.user.model.UserDTO;



@WebServlet("/user/register")
public class Register extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
    	System.out.println("회원가입");
        // 회원가입 페이지로 포워딩
        //request.getRequestDispatcher("/WEB-INF/views/user/auth/register.jsp").forward(request, response);
    	String googleClientId = System.getenv("GOOGLE_CLIENT_ID");
    	request.setAttribute("googleClientId", googleClientId);
    	request.getRequestDispatcher("/WEB-INF/views/user/auth/register.jsp").forward(request, response);

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // 요청 파라미터 인코딩 설정
        request.setCharacterEncoding("UTF-8");
        
        // 회원가입 폼에서 전송된 데이터 받기
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String name = request.getParameter("name");
        //String phoneNumber = request.getParameter("phoneNumber");
        
        // 세션에서 인증된 이메일 확인
        HttpSession session = request.getSession();
        String verifiedEmail = (String) session.getAttribute("verified_email");
        
        if (verifiedEmail == null || !verifiedEmail.equals(email)) {
            request.setAttribute("errorMessage", "이메일 인증이 필요합니다.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/register.jsp").forward(request, response);
            return;
        }
        
        // 입력값 검증
        if (email == null || password == null || name == null ||
            email.trim().isEmpty() || password.trim().isEmpty() || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "모든 필수 항목을 입력해주세요.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/register.jsp").forward(request, response);
            return;
        }
        
        // 비밀번호 확인 일치 검증
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/register.jsp").forward(request, response);
            return;
        }
        
        // 이메일 중복 확인
        if (userDAO.isEmailExists(email)) {
            request.setAttribute("errorMessage", "이미 사용 중인 이메일입니다.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/register.jsp").forward(request, response);
            return;
        }
        
        // 비밀번호 암호화
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        // 사용자 객체 생성
        UserDTO user = new UserDTO(email, hashedPassword, name); // phoneNumber 제거
        
        // 회원가입 처리
        int result = userDAO.registerUser(user);
        
        if (result > 0) {
            // 회원가입 성공
            response.sendRedirect(request.getContextPath() + "/user/login?registered=true");
        } else {
            // 회원가입 실패
            request.setAttribute("errorMessage", "회원가입에 실패했습니다. 다시 시도해주세요.");
            request.getRequestDispatcher("/WEB-INF/views/user/auth/register.jsp").forward(request, response);
        }
    }
}
