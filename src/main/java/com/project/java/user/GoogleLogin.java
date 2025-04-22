//// src/main/java/com/project/java/user/GoogleLoginServlet.java
//package com.project.java.user;
//
//import java.io.IOException;
//import java.util.Collections;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.json.simple.JSONObject;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.project.java.user.model.UserDAO;
//import com.project.java.user.model.UserDTO;
//
//
//
//@WebServlet("/user/google-login")
//public class GoogleLogin extends HttpServlet {
//    private UserDAO userDAO = new UserDAO();
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        System.out.println("구글 로그인 처리 시작");
//        
//        String idTokenString = request.getParameter("idToken");
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        
//        try {
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
//                .setAudience(Collections.singletonList(System.getenv("GOOGLE_CLIENT_ID")))
//                .build();
//
//            GoogleIdToken idToken = verifier.verify(idTokenString);
//            if (idToken != null) {
//                Payload payload = idToken.getPayload();
//                String email = payload.getEmail();
//                String name = (String) payload.get("name");
//                String profileImage = (String) payload.get("picture");
//
//                UserDTO existingUser = userDAO.getUserByEmail(email);
//                if (existingUser == null) {
//                    // 신규 사용자 회원가입 처리
//                    UserDTO newUser = new UserDTO();
//                    newUser.setEmail(email);
//                    newUser.setName(name);
//                    newUser.setProfileImage(profileImage);
//                    newUser.setSocialType("google");
//                    userDAO.registerUser(newUser);
//                    System.out.println("새로운 Google 사용자 등록: " + email);
//                    sendResponse(response, true, "회원가입이 완료되었습니다.", "/user/welcome");
//                } else {
//                    // 기존 사용자 로그인 처리
//                    HttpSession session = request.getSession();
//                    session.setAttribute("user", existingUser);
//                    System.out.println("기존 Google 사용자 로그인: " + email);
//                    sendResponse(response, true, "로그인 성공", "/index.do");
//                }
//            } else {
//                System.out.println("유효하지 않은 ID 토큰");
//                sendResponse(response, false, "유효하지 않은 인증 토큰입니다.", null);
//            }
//        } catch (Exception e) {
//            System.out.println("Google 로그인 처리 중 오류 발생: " + e.getMessage());
//            e.printStackTrace();
//            sendResponse(response, false, "서버 오류가 발생했습니다: " + e.getMessage(), null);
//        }
//    }
//
//    private void sendResponse(HttpServletResponse response, boolean success, String message, String redirect) throws IOException {
//        JSONObject jsonResponse = new JSONObject();
//        jsonResponse.put("success", success);
//        jsonResponse.put("message", message);
//        if (redirect != null) {
//            jsonResponse.put("redirect", redirect);
//        }
//        response.getWriter().write(jsonResponse.toString());
//    }
//}
