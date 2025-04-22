package com.project.java.devtest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class UserTechScoreDAO {
	private DataSource dataSource;

	public UserTechScoreDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertOrUpdateTechScore(int userSeq, int optionSeq) {
		String query = "SELECT techSeq, score FROM tblOptionTechScore WHERE optionSeq = ?";
		String mergeQuery = "MERGE INTO tblUserTechScore uts "
				+ "USING (SELECT ? AS userSeq, ? AS techSeq, ? AS totalScore FROM DUAL) newData "
				+ "ON (uts.userSeq = newData.userSeq AND uts.techSeq = newData.techSeq) " + "WHEN MATCHED THEN "
				+ "    UPDATE SET uts.totalScore = uts.totalScore + newData.totalScore " + "WHEN NOT MATCHED THEN "
				+ "    INSERT (userTechScoreSeq, userSeq, techSeq, totalScore) "
				+ "    VALUES (seqTblUserTechScore.NEXTVAL, newData.userSeq, newData.techSeq, newData.totalScore)";

		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false); // 🔹 자동 커밋 해제!

			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, optionSeq);
				ResultSet rs = pstmt.executeQuery();

				System.out.println(
						"🔹 SQL 실행: SELECT techSeq, score FROM tblOptionTechScore WHERE optionSeq = " + optionSeq);

				try (PreparedStatement mergeStmt = conn.prepareStatement(mergeQuery)) {
					while (rs.next()) {
						mergeStmt.setInt(1, userSeq);
						mergeStmt.setInt(2, rs.getInt("techSeq"));
						mergeStmt.setInt(3, rs.getInt("score"));
						mergeStmt.addBatch();
					}
					mergeStmt.executeBatch();
					conn.commit(); // 🔹 명시적인 커밋 수행!
					System.out.println("✅ COMMIT 수행 완료!");
				}
			}
		} catch (SQLException e) {
			System.out.println("🚨 SQL 실행 중 예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateRecommendedTech(int userSeq) {
		String query = "UPDATE tblUserTechScore uts " + "SET isRecommended = 'Y' " + "WHERE uts.techSeq IN ("
				+ "   SELECT techSeq " + "   FROM (" + "      SELECT uts.techSeq, t.categorySeq, uts.totalScore, "
				+ "             RANK() OVER (PARTITION BY uts.userSeq, t.categorySeq ORDER BY uts.totalScore DESC) AS rank "
				+ "      FROM tblUserTechScore uts " + "      JOIN tblTech t ON uts.techSeq = t.techSeq "
				+ "      WHERE uts.userSeq = ? " + "   ) " + "   WHERE rank = 1" + ")";

		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false); // 🔹 자동 커밋 해제!

			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, userSeq);
				System.out.println("🔹 updateRecommendedTech() 실행: userSeq = " + userSeq); // ✅ 디버깅 코드 추가!
				int updatedRows = pstmt.executeUpdate();
				conn.commit(); //
				System.out.println("✅ updateRecommendedTech() 실행 완료! 업데이트된 행 개수: " + updatedRows);
			}
		} catch (SQLException e) {
			System.out.println("🚨 SQL 실행 중 예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public List<UserTechScoreDTO> getUserTechScores(int userSeq) {
		List<UserTechScoreDTO> techScores = new ArrayList<>();
		String query = "SELECT t.techSeq, COALESCE(uts.totalScore, 0) AS totalScore, "
				+ "COALESCE(uts.isRecommended, 'N') AS isRecommended, t.techName, t.categorySeq " + "FROM tblTech t "
				+ "LEFT JOIN tblUserTechScore uts ON t.techSeq = uts.techSeq AND uts.userSeq = ? "
				+ "ORDER BY t.categorySeq, uts.totalScore DESC";

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, userSeq);
			System.out.println("🔹 getUserTechScores() 실행: userSeq = " + userSeq);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				UserTechScoreDTO dto = new UserTechScoreDTO();
				dto.setTechSeq(rs.getInt("techSeq"));
				dto.setTotalScore(rs.getInt("totalScore"));
				dto.setIsRecommended(
						rs.getString("isRecommended") != null ? rs.getString("isRecommended").trim() : "N");
				dto.setTechName(rs.getString("techName"));
				dto.setCategorySeq(rs.getInt("categorySeq"));
				techScores.add(dto);
				/*
				 * System.out.println("✅ 조회된 데이터: techName = " + rs.getString("techName") +
				 * ", totalScore = " + rs.getInt("totalScore"));
				 */
			}
			System.out.println("데이터 조회ing...");
			
		} catch (SQLException e) {
			System.out.println("🚨 SQL 실행 중 예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("🔹 최종 조회된 개수: " + techScores.size());
		return techScores;
	}

	public void deleteUserScores(int userSeq, HttpSession session) {
		String query = "DELETE FROM tblUserTechScore WHERE userSeq = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, userSeq);
			int deletedRows = pstmt.executeUpdate();
			System.out.println("✅ 사용자 점수 삭제 완료! 삭제된 행 개수: " + deletedRows);

			// 🔹 세션 상태 초기화
			if (session != null) {
				session.setAttribute("isSubmitted", false);
				System.out.println("🔹 세션 상태 초기화 완료: isSubmitted = false");
			}
		} catch (SQLException e) {
			System.out.println("🚨 SQL 실행 중 예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 테스트를 진행한 사용자의 devtest 컬럼을 Y값으로 설정하는 메소드
	 * 로드맵 페이지가 실행될 때 작동
	 * @param userSeq
	 */
	public void updateDevTestStatus(int userSeq) {
		System.out.println("updateDevTestStatus() 구문에 진입");
		
		String query = "UPDATE tblUser SET devtest = 'Y' WHERE userSeq = ?";

		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false); // 🔹 자동 커밋 해제!

			try (PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, userSeq);
				System.out.println("🔹 updateDevTestStatus() 실행: userSeq = " + userSeq); // ✅ 디버깅 코드 추가!
				int updatedRows = pstmt.executeUpdate();
				conn.commit(); //
				System.out.println("✅ updateDevTestStatus()) 실행 완료! 업데이트된 행 개수: " + updatedRows);
			}
		} catch (SQLException e) {
			System.out.println("🚨 SQL 실행 중 예외 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}
}