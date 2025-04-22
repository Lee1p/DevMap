package com.project.java.devtest;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.project.java.user.model.UserDAO;
import com.project.java.user.model.UserDTO;



@WebServlet("/devtest/DevFieldSelectionNoExp.do")
/**
 *  코딩경험 질문에 '아니오'를 선택한 다음 페이지
 *  사용자가 원하는 개발자 유형을 선택할 수 있는 페이지
 */
public class DevFieldSelectionNoExpServlet extends HttpServlet {
	private UserDAO userDAO;
	public static int fieldSeq = 0;
	
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
    }
    
    //개발 분야 선택하는 JSP
    @Override
    /**
     * 개발자 유형을 선택할 수 있는 페이지를 구현하는 GET 메소드
     * 세션에 저장된 user 정보
     * @Method : doGet
     * @return : void
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//int userSeq = 1;
    	UserDTO user = userDAO.selectUser(DevTypeTest.userSeq); 
    	req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/views/devtest/devFieldSelectionNoExp.jsp").forward(req, resp);
        
    }
    
    @Override
    /**
     * 개발자 유형을 선택하고 다음 페이지로 넘어가는 POST 메소드
     * 유형 확인 페이지로 넘어가거나 뒤로가기 버튼으로 이전 페이지로 돌아갈 수 있다.
     * 사용자가 선택한 버튼으로 세션에 fieldSeq번호가 저장 
     * @Method : doPost
     * @return : void
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String checkNoExp = req.getParameter("checkNoExp");
    	String fieldnum = req.getParameter("field");
    	
    	
        if ("no".equals(checkNoExp)) {
            resp.sendRedirect(req.getContextPath() + "/devtest/CodingQuestion.do");
        } else {
        	
        	if("1".equals(fieldnum)) {
        		fieldSeq = 1;
        		System.out.printf("개발자유형: %s %s ",fieldnum,checkNoExp);
        	} else if("2".equals(fieldnum)) {
        		fieldSeq = 2;
        		System.out.printf("개발자유형: %s %s ",fieldnum,checkNoExp);
        	} else {
        		fieldSeq = 3;
        		System.out.printf("개발자유형: %s %s ",fieldnum,checkNoExp);
        	}
        	req.setAttribute("fieldSeq", fieldSeq);	
  
            resp.sendRedirect(req.getContextPath() + "/devtest/NoExpSelectDevField.do");
            System.out.println("개발자유형 선택을 완료하고 체크");
        }
    }
}
