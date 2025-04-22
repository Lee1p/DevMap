package com.project.java.test.quiz.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 퀴즈 정보를 처리하는 DAO입니다.
 */
public class QuizDAO {
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;

	// 데이터베이스 연결
	private Connection getConnection() throws Exception {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
		return ds.getConnection();
	}

	// 자원 해제
	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (pstat != null)
				pstat.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 특정 과목의 모든 퀴즈를 가져오는 메서드
	 * 
	 * @param subjectSeq 과목 시퀀스 번호
	 * @return 퀴즈 목록
	 */
	public ArrayList<QuizDTO> getQuizListBySubject(int subjectSeq) {
		ArrayList<QuizDTO> list = new ArrayList<>();
		try {
			conn = getConnection();
			String sql = "SELECT * FROM tblQuiz WHERE subjectSeq = ? ORDER BY quizSeq";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, subjectSeq);
			rs = pstat.executeQuery();

			while (rs.next()) {
				QuizDTO dto = new QuizDTO();
				dto.setQuizSeq(rs.getInt("quizSeq"));
				dto.setSubjectSeq(rs.getInt("subjectSeq"));
				dto.setQuizTitle(rs.getString("quizTitle"));
				dto.setOptionA(rs.getString("optionA"));
				dto.setOptionB(rs.getString("optionB"));
				dto.setOptionC(rs.getString("optionC"));
				dto.setOptionD(rs.getString("optionD"));
				dto.setCorrectAnswer(rs.getString("correctAnswer"));
				dto.setQuizScore(rs.getInt("quizScore"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setModDate(rs.getString("modDate"));

				// 해설 필드 추가
				try {
					dto.setExplanation(rs.getString("explanation"));
				} catch (Exception e) {
					// 필드가 없는 경우 무시
				}

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	/**
	 * 특정 퀴즈의 상세 정보를 가져오는 메서드
	 * 
	 * @param quizSeq 퀴즈 시퀀스 번호
	 * @return 퀴즈 정보
	 */
	public QuizDTO getQuiz(int quizSeq) {
		QuizDTO dto = null;
		try {
			conn = getConnection();
			String sql = "SELECT * FROM tblQuiz WHERE quizSeq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, quizSeq);
			rs = pstat.executeQuery();

			if (rs.next()) {
				dto = new QuizDTO();
				dto.setQuizSeq(rs.getInt("quizSeq"));
				dto.setSubjectSeq(rs.getInt("subjectSeq"));
				dto.setQuizTitle(rs.getString("quizTitle"));
				dto.setOptionA(rs.getString("optionA"));
				dto.setOptionB(rs.getString("optionB"));
				dto.setOptionC(rs.getString("optionC"));
				dto.setOptionD(rs.getString("optionD"));
				dto.setCorrectAnswer(rs.getString("correctAnswer"));
				dto.setQuizScore(rs.getInt("quizScore"));
				dto.setRegDate(rs.getString("regDate"));
				dto.setModDate(rs.getString("modDate"));

				// 해설 필드 추가
				try {
					dto.setExplanation(rs.getString("explanation"));
				} catch (Exception e) {
					// 필드가 없는 경우 무시
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return dto;
	}

	/**
	 * 특정 과목의 첫 번째 퀴즈 번호를 가져오는 메서드
	 * 
	 * @param subjectSeq 과목 시퀀스 번호
	 * @return 첫 번째 퀴즈 번호
	 */
	public int getFirstQuizSeqBySubject(int subjectSeq) {
		int firstQuizSeq = 0;
		try {
			conn = getConnection();
			String sql = "SELECT MIN(quizSeq) AS firstQuizSeq FROM tblQuiz WHERE subjectSeq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, subjectSeq);
			rs = pstat.executeQuery();

			if (rs.next()) {
				firstQuizSeq = rs.getInt("firstQuizSeq");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return firstQuizSeq;
	}

	/**
	 * 다음 퀴즈 번호를 가져오는 메서드
	 * 
	 * @param currentQuizSeq 현재 퀴즈 시퀀스 번호
	 * @param subjectSeq     과목 시퀀스 번호
	 * @return 다음 퀴즈 번호 (없으면 -1)
	 */
	public int getNextQuizSeq(int currentQuizSeq, int subjectSeq) {
		int nextQuizSeq = -1;
		try {
			conn = getConnection();
			String sql = "SELECT MIN(quizSeq) AS nextQuizSeq FROM tblQuiz WHERE subjectSeq = ? AND quizSeq > ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, subjectSeq);
			pstat.setInt(2, currentQuizSeq);
			rs = pstat.executeQuery();

			if (rs.next()) {
				nextQuizSeq = rs.getInt("nextQuizSeq");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return nextQuizSeq;
	}

	/**
	 * 사용자의 퀴즈 결과를 저장하는 메서드
	 * 
	 * @param userSeq        사용자 시퀀스 번호
	 * @param quizSeq        퀴즈 시퀀스 번호
	 * @param selectedAnswer 사용자가 선택한 답변
	 * @param isCorrect      정답 여부 (Y/N)
	 * @return 저장 성공 여부 (1: 성공, 0: 실패)
	 */
	public int saveQuizResult(int userSeq, int quizSeq, String selectedAnswer, String isCorrect) {
		int result = 0;
		try {
			conn = getConnection();
			String sql = "INSERT INTO tblUserQuizResult (userQuizResultSeq, userSeq, quizSeq, selectedAnswer, isCorrect, attemptDate) "
					+ "VALUES ((SELECT NVL(MAX(userQuizResultSeq), 0) + 1 FROM tblUserQuizResult), ?, ?, ?, ?, SYSDATE)";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, userSeq);
			pstat.setInt(2, quizSeq);
			pstat.setString(3, selectedAnswer);
			pstat.setString(4, isCorrect);
			result = pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	/**
	 * 사용자의 특정 과목 퀴즈 결과를 가져오는 메서드
	 * 
	 * @param userSeq    사용자 시퀀스 번호
	 * @param subjectSeq 과목 시퀀스 번호
	 * @return 퀴즈 결과 목록
	 */
	public ArrayList<UserQuizResultDTO> getQuizResults(int userSeq, int subjectSeq) {
		ArrayList<UserQuizResultDTO> list = new ArrayList<>();
		try {
			conn = getConnection();
			String sql = "SELECT r.*, q.quizTitle, q.correctAnswer, q.quizScore, q.explanation "
					+ "FROM tblUserQuizResult r " + "JOIN tblQuiz q ON r.quizSeq = q.quizSeq "
					+ "WHERE r.userSeq = ? AND q.subjectSeq = ? " + "ORDER BY r.quizSeq";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, userSeq);
			pstat.setInt(2, subjectSeq);
			rs = pstat.executeQuery();

			while (rs.next()) {
				UserQuizResultDTO dto = new UserQuizResultDTO();
				dto.setUserQuizResultSeq(rs.getInt("userQuizResultSeq"));
				dto.setUserSeq(rs.getInt("userSeq"));
				dto.setQuizSeq(rs.getInt("quizSeq"));
				dto.setSelectedAnswer(rs.getString("selectedAnswer"));
				dto.setIsCorrect(rs.getString("isCorrect"));
				dto.setAttemptDate(rs.getString("attemptDate"));
				dto.setQuizTitle(rs.getString("quizTitle"));
				dto.setCorrectAnswer(rs.getString("correctAnswer"));
				dto.setQuizScore(rs.getInt("quizScore"));

				// 해설 필드 추가
				try {
					dto.setExplanation(rs.getString("explanation"));
				} catch (Exception e) {
					// 필드가 없는 경우 무시
				}

				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	/**
	 * 사용자의 특정 과목 퀴즈 총점을 계산하는 메서드
	 * 
	 * @param userSeq    사용자 시퀀스 번호
	 * @param subjectSeq 과목 시퀀스 번호
	 * @return 총점
	 */
	public int calculateTotalScore(int userSeq, int subjectSeq) {
		int totalScore = 0;
		try {
			conn = getConnection();
			String sql = "SELECT SUM(q.quizScore) AS totalScore " + "FROM tblUserQuizResult r "
					+ "JOIN tblQuiz q ON r.quizSeq = q.quizSeq "
					+ "WHERE r.userSeq = ? AND q.subjectSeq = ? AND r.isCorrect = 'Y'";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, userSeq);
			pstat.setInt(2, subjectSeq);
			rs = pstat.executeQuery();

			if (rs.next()) {
				totalScore = rs.getInt("totalScore");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return totalScore;
	}

	/**
	 * 이전 퀴즈 번호를 가져오는 메서드
	 * 
	 * @param currentQuizSeq 현재 퀴즈 시퀀스 번호
	 * @param subjectSeq     과목 시퀀스 번호
	 * @return 이전 퀴즈 번호 (없으면 -1)
	 */
	public int getPrevQuizSeq(int currentQuizSeq, int subjectSeq) {
		int prevQuizSeq = -1;
		try {
			conn = getConnection();
			String sql = "SELECT MAX(quizSeq) AS prevQuizSeq FROM tblQuiz WHERE subjectSeq = ? AND quizSeq < ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, subjectSeq);
			pstat.setInt(2, currentQuizSeq);
			rs = pstat.executeQuery();

			if (rs.next()) {
				prevQuizSeq = rs.getInt("prevQuizSeq");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return prevQuizSeq;
	}

	/**
	 * 사용자의 특정 퀴즈 결과를 삭제하는 메서드 (최신 결과만 유지하기 위함)
	 * 
	 * @param userSeq 사용자 시퀀스 번호
	 * @param quizSeq 퀴즈 시퀀스 번호
	 * @return 삭제된 행 수
	 */
	public int deleteQuizResult(int userSeq, int quizSeq) {
		int result = 0;
		try {
			conn = getConnection();
			String sql = "DELETE FROM tblUserQuizResult WHERE userSeq = ? AND quizSeq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, userSeq);
			pstat.setInt(2, quizSeq);
			result = pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	/**
	 * 사용자의 특정 과목 퀴즈 결과를 모두 삭제하는 메서드
	 * 
	 * @param userSeq    사용자 시퀀스 번호
	 * @param subjectSeq 과목 시퀀스 번호
	 * @return 삭제된 행 수
	 */
	public int deleteAllQuizResults(int userSeq, int subjectSeq) {
		int result = 0;
		try {
			conn = getConnection();
			String sql = "DELETE FROM tblUserQuizResult WHERE userSeq = ? AND quizSeq IN (SELECT quizSeq FROM tblQuiz WHERE subjectSeq = ?)";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, userSeq);
			pstat.setInt(2, subjectSeq);
			result = pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	/**
	 * 사용자가 이미 답변한 문제 목록을 가져오는 메서드
	 * 
	 * @param userSeq    사용자 시퀀스 번호
	 * @param subjectSeq 과목 시퀀스 번호
	 * @return 답변한 퀴즈 번호 목록
	 */
	public List<Integer> getAnsweredQuizzes(int userSeq, int subjectSeq) {
		List<Integer> list = new ArrayList<>();
		try {
			conn = getConnection();
			String sql = "SELECT DISTINCT q.quizSeq " + "FROM tblQuiz q "
					+ "JOIN tblUserQuizResult r ON q.quizSeq = r.quizSeq " + "WHERE q.subjectSeq = ? AND r.userSeq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, subjectSeq);
			pstat.setInt(2, userSeq);
			rs = pstat.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("quizSeq"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

}
