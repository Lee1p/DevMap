package com.project.java.devtest;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.devtest.model.DevFieldDAO;
import com.project.java.devtest.model.DevFieldDTO;
import com.project.java.roadmap.model.RoadMapDAO;
import com.project.java.user.model.UserDAO;
import com.project.java.user.model.UserDTO;
import com.project.java.devtest.DevTypeTest;
import com.project.java.devtest.DevFieldSelectionNoExpServlet;

@WebServlet("/devtest/NoExpSelectDevField.do")
/**
 * 사용자가 선택한 개발자 유형을 확인하는 페이지
 * 
 */
public class NoExpSelectDevField extends HttpServlet {
	private UserDAO userDAO;
	private DevFieldDAO devFieldDAO;

	@Override
    /**
     * DB에서 가져온 데이터를 옮겨담을 기본 객체생성 메소드
     * 
     * @Method : init
     * @return : void
     * @throws ServletException
     */
    public void init() throws ServletException {
        userDAO = new UserDAO(); // UserDAO 객체 초기화
        devFieldDAO = new DevFieldDAO();
    }
	
	@Override
	/**
	 * 사용자가 선택한 개발자 유형을 확인할 수 있는 페이지를 구현하는 GET 메소드
	 * 세션에 저장된 user, fieldSeq 정보
	 * @Method : doGet
	 * @return : void
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("NoExpSelectDevField 파일 doGet문 실행");
		//int userSeq = 1;
		//int fieldSeq = 2;
		
		HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/devmap/user/login.do");
            return;
        }
		
        int userSeq = (int) session.getAttribute("userSeq");
		UserDTO user = userDAO.selectUser(userSeq); 
    	DevFieldDTO devfield = devFieldDAO.getDevFieldById(DevFieldSelectionNoExpServlet.fieldSeq);
    	
    	req.setAttribute("user", user);
    	req.setAttribute("devfield", devfield);
		
		// 선택한 개발자 유형이 맞는지 확인하는 페이지로 이동
		req.getRequestDispatcher("/WEB-INF/views/devtest/selectedDevFieldCheck.jsp").forward(req, resp);
	}
	
	@Override
	/**
	 * 사용자가 선택한 개발자 유형에 따라 동적인 로드맵 페이지로 넘어가는 POST 메소드
	 * 결과보기 하면 로드맵페이지로 or 다시선택하기 하면 개발자 유형을 선택하는 페이지로 이동
	 * @Method : doPost
	 * @return : void
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 결과보기 하면 로드맵페이지로 or 다시선택하기 하면 개발자 유형을 선택하는 페이지로 이동
		String checkRoadMap = req.getParameter("checkRoadMap");
		int userSeq = DevTypeTest.userSeq; // 현재 로그인한 유저 ID 가져오기

		   if ("yes".equals(checkRoadMap)) {
	            // ✅ devtest 값을 'Y'로 업데이트
			   RoadMapDAO roadMapDAO = new RoadMapDAO(); // ✅ DAO 객체 생성
			   int result = roadMapDAO.updateIsActive(String.valueOf(userSeq)); // ✅ 객체 통해 호출
	            if (result > 0) {
	                System.out.println("devtest 업데이트 성공!");
	            } else {
	                System.out.println("devtest 업데이트 실패!");
	            }

	            // ✅ 로드맵 페이지로 이동
	            resp.sendRedirect(req.getContextPath() + "/roadmap/roadmapf.do");

	        } else {
	            // ✅ 다시 선택 페이지로 이동
	            resp.sendRedirect(req.getContextPath() + "/devtest/DevFieldSelectionNoExp.do");
	        }
	    }
	}
