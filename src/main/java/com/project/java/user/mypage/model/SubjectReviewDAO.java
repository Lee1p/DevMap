package com.project.java.user.mypage.model;

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
 * 망각곡선 리스트에 출력할 과목 결과의 DAO입니다.
 */
public class SubjectReviewDAO {
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private DataSource ds;


	public SubjectReviewDAO() {
		try {
			Context ctx = new InitialContext();
			Context env = (Context)ctx.lookup("java:comp/env");
			DataSource ds = (DataSource)env.lookup("jdbc/pool");

			conn = ds.getConnection();
			stat = conn.createStatement();



		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 리소스 닫기 (공통 메서드)
    public void close(Connection conn, PreparedStatement pstat, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstat != null) pstat.close();
            if (conn != null) conn.close();  // 커넥션 풀에 반환됨
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	//리스트에서 받아오기
    /**
     * 과목 점수를 리스트로 받아옵니다.
     * 기억력 점수가 낮은 순으로 출력합니다.
     * @param userSeq
     * @return
     */
	public ArrayList<SubjectReviewDTO> getReviewList(int userSeq) {
		try {

			String sql = "select * from tblSubjectReview r\r\n"
					+ "    inner join tblsubject s\r\n"
					+ "        on r.subjectseq = s.subjectseq\r\n"
					+ "            where userseq = ?\r\n"
					+ "             order by memoryscore desc";

			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, userSeq);

			rs = pstat.executeQuery();
			
			ArrayList<SubjectReviewDTO> list = new ArrayList<SubjectReviewDTO>();

			while(rs.next()) {
				SubjectReviewDTO result = new SubjectReviewDTO();
				
				result.setUserSeq(rs.getInt("reviewSeq"));
				result.setUserSeq(rs.getInt("userSeq"));
				result.setSubjectSeq(rs.getString("subjectSeq"));
				result.setLastReviewDate(rs.getString("lastReviewDate"));
				result.setMemoryScore(rs.getString("memoryScore"));
				result.setIsComplete(rs.getString("isComplete"));
				result.setSubjectName(rs.getString("subjectName"));
				

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
