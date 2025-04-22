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
 * 랭킹 지급 내역과 관련된 정보를 처리하는 DAO입니다.
 * <p>모든 사용자의 랭킹 점수를 집계, 평균 내어 유저와 비교하는 그래프를 제공합니다.</p>
 * <p>마이페이지 유저 정보와 그래프에서 조회될 유저 개인의 랭킹 점수를 보여줍니다.</p>
 * 
 */
public class RatingHistoryDAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private DataSource ds;


	public RatingHistoryDAO() {
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

    /**
     * 유저 개인의 랭킹 점수를 확인합니다.
     * 유저가 최근 일주일 내의 점수들만 집계하여 그 날의 평균 획득 점수를 조회합니다.
     * @param userSeq 유저 고유 번호
     * @return 점수 리스트
     */
	public ArrayList<RatingHistoryDTO> getratinghistory(int userSeq) {
		try {

			String sql = "SELECT changedAt, \r\n"
					+ "       SUM(total_rating) OVER (ORDER BY changedAt) AS cumulative_rating\r\n"
					+ "FROM (\r\n"
					+ "    SELECT changedAt, SUM(rating) AS total_rating\r\n"
					+ "    FROM tblUserRatingHistory\r\n"
					+ "    WHERE userseq = ?\r\n"
					+ "    GROUP BY changedAt\r\n"
					+ "    ORDER BY changedAt DESC\r\n"
					+ ") \r\n"
					+ "WHERE ROWNUM <= 6";

			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, userSeq);

			rs = pstat.executeQuery();
			
			ArrayList<RatingHistoryDTO> list = new ArrayList<RatingHistoryDTO>();

			while(rs.next()) {
				RatingHistoryDTO result = new RatingHistoryDTO();
				
				
				result.setRating(rs.getString("cumulative_rating"));
				result.setChangedAt(rs.getString("changedAt"));
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
     * 전체 사용자 랭킹 점수의 평균 조회합니다.
     * 마이페이지에서 유저의 랭킹 점수와 비교하는 그래프에서 사용됩니다.
     * @return 전체 유저들의 날짜별 평균 점수 리턴
     */
	public ArrayList<RatingHistoryDTO> getratinghistoryall() {
		try {
			Context ctx = new InitialContext();
			Context env = (Context)ctx.lookup("java:comp/env");
			DataSource ds = (DataSource)env.lookup("jdbc/pool");

			conn = ds.getConnection();
			stat = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {

			String sql = "SELECT changedAt, \r\n"
					+ "       AVG(cumulative_rating) AS avg_cumulative_rating\r\n"
					+ "FROM (\r\n"
					+ "    SELECT changedAt, \r\n"
					+ "           SUM(rating) OVER (PARTITION BY userseq ORDER BY changedAt) AS cumulative_rating\r\n"
					+ "    FROM tblUserRatingHistory\r\n"
					+ ") \r\n"
					+ "GROUP BY changedAt\r\n"
					+ "ORDER BY changedAt DESC\r\n"
					+ "FETCH FIRST 6 ROWS ONLY";

			pstat = conn.prepareStatement(sql);

			rs = pstat.executeQuery();
			
			ArrayList<RatingHistoryDTO> list = new ArrayList<RatingHistoryDTO>();

			while(rs.next()) {
				RatingHistoryDTO result = new RatingHistoryDTO();
				
				
				result.setRating(rs.getString("avg_cumulative_rating"));
				result.setChangedAt(rs.getString("changedAt"));
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
