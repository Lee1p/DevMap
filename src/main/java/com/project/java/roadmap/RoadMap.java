package com.project.java.roadmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.java.roadmap.model.RoadCategoryDTO;
import com.project.java.roadmap.model.RoadMapDAO;
import com.project.java.roadmap.model.RoadMapDTO;
import com.project.java.devtest.DevTypeTest;
import com.project.java.devtest.model.DevFieldDAO;
import com.project.java.devtest.model.DevFieldDTO;
import com.project.java.devtest.model.UserTechScoreDAO;
import com.project.java.devtest.model.UserTechScoreDTO;
import com.project.java.user.model.UserDAO;
import com.project.java.user.model.UserDTO;
import com.project.java.devtest.DevFieldSelectionNoExpServlet;


/**
 * 사용자 테스트 결과 데이터를 바탕으로 화면에 로드맵을 구현하는 페이지 
 * 
 */
@WebServlet("/roadmap/roadmap.do")
public class RoadMap extends HttpServlet {
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
        userDAO = new UserDAO(); 
        devFieldDAO = new DevFieldDAO();
        

    }

    @Override
    /**
	 * 로드맵 
	 * @param req 로드맵 화면에 보여줄 사용자 테스트 결과 데이터
	 * @param resp 이전 페이지에서 사용자 로그인을 확인하기 위해 받을 사용자 정보 데이터
	 */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	System.out.println("로드맵 시작페이지");
    	HttpSession session = req.getSession();
        if (session.getAttribute("userSeq") == null) {
            resp.sendRedirect("/devmap/user/login.do");
            return;
        }
        int userSeq = (int) session.getAttribute("userSeq");
        String selectedField = (String) session.getAttribute("selectedField");
	    String selectedFieldName = (String) session.getAttribute("selectedFieldName");
    	
    	
    	
    	
    	UserDTO user = userDAO.selectUser(userSeq);
    	
    	DevFieldDTO devfield = devFieldDAO.getDevFieldById();
    	
    	UserTechScoreDAO userTechScoreDAO = new UserTechScoreDAO();
        List<UserTechScoreDTO> userTechScores = userTechScoreDAO.getUserTechScores(userSeq);
        
    	
        
        
    	RoadMapDAO dao = new RoadMapDAO();
    	ArrayList<RoadMapDTO> roadmaplist = dao.getRoadMapTechAll();
    	ArrayList<RoadCategoryDTO> roadmapcategory = dao.getRoadMapCategoryAll();
        
    	//tbluser 테이블의 컬럼 devtest 값 Y으로 업데이트 하는 구문 
    	userTechScoreDAO.updateDevTestStatus(userSeq);
    	
    	
    	
    	
    	System.out.println("객체 생성");
    	req.setAttribute("user", user);
    	req.setAttribute("devfield", devfield);
        req.setAttribute("roadmaplist", roadmaplist);
        req.setAttribute("roadmapcategory", roadmapcategory);
        req.setAttribute("userTechScores", userTechScores);
        
		/*
		 * System.out.println("req 속성 설정"); System.out.println(user.getName());
		 * System.out.println(devfield.getFieldName());
		 * System.out.println(roadmaplist.getFirst().getTechName());
		 * System.out.println(roadmapcategory.getFirst().getCategoryName());
		 * System.out.println(userTechScores.getFirst().getTotalScore());
		 */
       
        req.getRequestDispatcher("/WEB-INF/views/roadmap/roadmapf.jsp").forward(req, resp);
        
        
    }

}