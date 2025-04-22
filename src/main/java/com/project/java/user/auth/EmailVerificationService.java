package com.project.java.user.auth;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * 사용자 이메일 인증을 받은 서블렛입니다.
 * 인증코드를 생성하고, 이메일을 발신하는 역할을 합니다.
 */
public class EmailVerificationService {
    
    // 인증 코드 생성
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 코드
        return String.valueOf(code);
    }
    
    // 이메일 전송
    public static boolean sendVerificationEmail(String to, String code) {
        final String username = "now4940@gmail.com"; // 발신 이메일
        final String password = "zgzx asmz hjgn rxml"; // 앱 비밀번호
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("개발자 로드맵 - 이메일 인증");
            message.setText("안녕하세요,\n\n"
                    + "개발자 로드맵 회원가입을 위한 인증 코드입니다: " + code
                    + "\n\n이 코드는 30분 동안 유효합니다.");
            
            Transport.send(message);
            System.out.println("이메일 전송 성공: " + to);// 로깅 
            return true;
        } catch (MessagingException e) {
        	System.out.println("이메일 전송 실패: " + e.getMessage());//로깅 추
            e.printStackTrace();
            return false;
        }
    }
    
 // 일반 이메일 전송 메서드
    public static boolean sendEmail(String to, String subject, String body) {
        final String username = "now4940@gmail.com"; // 발신 이메일
        final String password = "zgzx asmz hjgn rxml"; // 앱 비밀번호
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            
            Transport.send(message);
            System.out.println("이메일 전송 성공: " + to);
            return true;
        } catch (MessagingException e) {
            System.out.println("이메일 전송 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
