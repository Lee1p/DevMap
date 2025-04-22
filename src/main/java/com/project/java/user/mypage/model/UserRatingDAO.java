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
 * 유저가 획득한 실제 랭킹 점수와 관련된 모든 정보를 처리하는 DAO입니다.

 */
public class UserRatingDAO {
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private DataSource ds;


	public UserRatingDAO() {
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
    
    public void close() {
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
     * 모든 유저의 랭킹 점수를 받아 상위 5개만 출력합니다.
     * @return
     */
	public ArrayList<UserRatingDTO> getUserRatingList() {
		try {

			String sql = "SELECT userSeq, totalSolved, currentRating, lastUpdated, name, rn\r\n"
					+ "FROM (\r\n"
					+ "    SELECT r.userSeq, r.totalSolved, r.currentRating, r.lastUpdated, u.name,\r\n"
					+ "           ROW_NUMBER() OVER (ORDER BY r.currentRating DESC) AS rn\r\n"
					+ "    FROM tblUserRating r\r\n"
					+ "    INNER JOIN tblUser u ON r.userSeq = u.userSeq\r\n"
					+ ") \r\n"
					+ "WHERE rn <= 5";

			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			
			ArrayList<UserRatingDTO> list = new ArrayList<UserRatingDTO>();

			while(rs.next()) {
				UserRatingDTO result = new UserRatingDTO();
				
				
				result.setUserSeq(rs.getInt("userSeq"));
				result.setTotalSolved(rs.getString("totalSolved"));
				result.setCurrentRating(rs.getString("currentRating"));
				result.setLastUpdated(rs.getString("lastUpdated"));
				result.setName(rs.getString("name"));
				result.setRn(rs.getString("rn"));
				
				list.add(result);
			}
			
			return list;
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/* close(conn, pstat, rs); */
        }
		return null;
	}

	
	/**
	 * 사용자 개인의 랭킹 점수를 출력하는 메서드입니다.
	 * @param userSeq
	 * @return
	 */
	public UserRatingDTO getUserRating(int userSeq) {
		try {

			String sql = "select * from tblUserRating\r\n"
					+ "    where userseq=?";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, userSeq);
			rs = pstat.executeQuery();

			if(rs.next()) {

				UserRatingDTO result = new UserRatingDTO();

				result.setUserSeq(rs.getInt("userSeq"));
				result.setTotalSolved(rs.getString("totalSolved"));
				result.setCurrentRating(rs.getString("currentRating"));
				result.setLastUpdated(rs.getString("lastUpdated"));

				return result;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/* close(conn, pstat, rs); */
        }
		return null;
	}
}
