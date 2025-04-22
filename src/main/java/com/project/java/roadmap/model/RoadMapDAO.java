package com.project.java.roadmap.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * 로드맵 DAO
 * DB에서 카테고리, 기술 정보를 가져오는 함수와 생성자
 * 
 */
public class RoadMapDAO {
	
	private DataSource ds;
	
	/**
	 * DAO 생성자, DB연결 확인
	 */
    public RoadMapDAO() {
        try {
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            ds = (DataSource) env.lookup("jdbc/pool");

            if (ds != null) {
                System.out.println("DB 커넥션 풀 설정 완료");
            } else {
                System.out.println("DB 커넥션 풀 설정 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 // is_active = 'Y'로 업데이트하는 메서드
    /**
     * 테스트를 진행한 사용자의 devtest 컬럼을 Y값으로 설정하는 메소드
     * 
     * @return : int
     * @param userseq
     */
    public int updateIsActive(String userseq) {
        String sql = "UPDATE tbluser SET devtest = 'Y' WHERE userseq = ?";
        try (
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, userseq);
            return pstmt.executeUpdate(); // 성공하면 1 리턴
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 리소스 닫기 (공통 메서드)
    /**
     * DB읽을 때 사용했던 연결을 해제하는 메소드
     * 
     * @param conn
     * @param pstat
     * @param rs
     */
    public void close(Connection conn, PreparedStatement pstat, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstat != null) pstat.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 로드맵에 구현될 기술노드 데이터를 DB에서 가져오는 메소드
     * 
     * @return : ArrayList
     */
    public ArrayList<RoadMapDTO> getRoadMapTechAll() {
        Connection conn = null;
        PreparedStatement pstat = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            stat = conn.createStatement();
            
            String sql = "select * from TBLTECHCATEGORY t inner join tbltech c on c.categoryseq = t.categoryseq";
            rs = stat.executeQuery(sql);

            ArrayList<RoadMapDTO> list = new ArrayList<>();
            while (rs.next()) {
            	RoadMapDTO result = new RoadMapDTO();
            	result.setTechSeq(rs.getInt("techSeq"));
            	result.setTechName(rs.getString("techName"));
            	result.setTechDesc(rs.getString("techDesc"));
            	result.setTechImgUrl(rs.getString("techImgUrl"));
            	result.setTechLearningTime(rs.getString("techLearningTime"));
            	result.setRegDate(rs.getString("regDate"));
            	result.setModDate(rs.getString("modDate"));
            	result.setDelDate(rs.getString("delDate"));
            	result.setCategorySeq(rs.getString("categorySeq"));
            	result.setCategoryName(rs.getString("categoryName"));

                list.add(result);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstat, rs);
        }
        return null;
    }

    /**
     * 로드맵에 구현될 카테고리 노드 데이터를 DB에서 가져오는 메소드
     * 
     * @return : ArrayList
     * @return
     */
	public ArrayList<RoadCategoryDTO> getRoadMapCategoryAll() {
		Connection conn = null;
        PreparedStatement pstat = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            stat = conn.createStatement();
            
            String sql = "select * from TBLTECHCATEGORY";
            rs = stat.executeQuery(sql);

            ArrayList<RoadCategoryDTO> list = new ArrayList<>();
            while (rs.next()) {
            	RoadCategoryDTO result = new RoadCategoryDTO();
            	result.setCategorySeq(rs.getInt("categorySeq"));
            	result.setCategoryName(rs.getString("categoryName"));

                list.add(result);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstat, rs);
        }
		return null;
	}

}
