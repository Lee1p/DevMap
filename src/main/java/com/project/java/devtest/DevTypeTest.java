package com.project.java.devtest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.user.model.UserDAO;
import com.project.java.user.model.UserDTO;

/**
 * 신규 회원이 네비게이션 바에서 "로드맵" 버튼을 클릭했을 때,
 * "개발자 성향 테스트" 관련 view 또는 로드맵 페이지를 응답해주는 서블릿입니다.
 *
 * 이 서블릿은 다음과 같은 기능을 수행합니다
 * 
 *   사용자가 로그인 상태인지 확인
 *   사용자의 성향 테스트 진행 여부(devtest 필드 확인)
 *   테스트를 완료한 경우 로드맵 페이지로 리디렉션
 *   테스트를 아직 진행하지 않은 경우 테스트 소개 페이지로 포워딩
 * 
 *
 * 또한 '진행하기' 버튼 클릭 시, POST 요청을 받아 테스트 질문 페이지로 리디렉션합니다.
 *
 * @author 이재현
 * @version 1.0
 * @since 2025-04-12
 */
@WebServlet("/devtest.do")
public class DevTypeTest extends HttpServlet {


	public static int userSeq;
	
	private UserDAO userDAO;
	// GET 요청 처리: 'devTestIntro.jsp' 페이지를 사용자에게 보여주는 역할

	
	/**
	 * 서블릿 초기화 메서드로, UserDAO 객체를 생성하여 초기화합니다.
	 *
	 * @throws ServletException 서블릿 초기화 중 발생한 예외
	 */
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO(); // UserDAO 객체 초기화

	}
	
	/**
	 * GET 요청을 처리하여 사용자의 성향 테스트 진행 여부에 따라 다음 페이지를 응답합니다.
	 *
	 * 
	 *   세션에 userSeq가 없는 경우 로그인 페이지로 리디렉션
	 *   사용자 정보가 DB에 없는 경우 404 오류 반환
	 *   테스트 완료한 사용자: 로드맵 페이지로 리디렉션
	 *   테스트 미완료 사용자: 성향 테스트 소개 페이지로 포워딩
	 * 
	 *
	 * @param req 클라이언트의 요청 정보(HttpServletRequest)
	 * @param resp 클라이언트에게 응답을 보내기 위한 HttpServletResponse
	 * @throws ServletException 서블릿 처리 중 발생한 예외
	 * @throws IOException 입출력 처리 중 발생한 예외
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    

		 HttpSession session = req.getSession();
	        if (session.getAttribute("userSeq") == null) {
	            resp.sendRedirect("/devmap/user/login.do");
	            return;
	        }
	        
	    int userSeq = (int) session.getAttribute("userSeq");
	    UserDTO user = userDAO.selectUser(userSeq);
	    
	    if (user == null) {
	        System.out.println(" 사용자 정보를 찾을 수 없습니다. userSeq: " + userSeq);
	        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "사용자 정보를 찾을 수 없습니다.");
	        return;
	    }
	    
	    req.setAttribute("user", user);
	    System.out.printf("유저값 %s, %d, %s \n",user.getName(),user.getUserSeq(),user.getDevtest());
	    
	    
	    
	    
	    if (user != null && "Y".equals(user.getDevtest())) {
	        // isActive가 'Y'라면 roadmap 페이지로 이동
	    	System.out.println("테스트 하신 분입니다.");
	    	System.out.printf("%b",user != null && "Y".equals(user.getDevtest()));
	    	resp.sendRedirect(req.getContextPath() + "/roadmap/roadmap.do");
	    } else {
	        // isActive가 'N'이거나 값이 없으면 devTestIntro.jsp로 이동
	    	System.out.println("테스트 안하신 분입니다.");
	    	System.out.printf("%b",user != null && "Y".equals(user.getDevtest()));
	        req.getRequestDispatcher("/WEB-INF/views/devtest/devTestIntro.jsp").forward(req, resp);
	    }
	    
	    System.out.println("...");
	}
	// POST 요청 처리: '진행하기' 버튼 클릭 후, 코딩 경험 질문 페이지로 리디렉션
	// 사용자가 진행을 시작하는 것에 대한 상태나 데이터를 서버에서 처리하고 저장하기
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// '진행하기' 버튼 클릭 후, /devtest/CodingQuestion.do로 리디렉션
		resp.sendRedirect(req.getContextPath() + "/devtest/CodingQuestion.do");
	}
}
