package com.project.java.test.codetest.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 코드 테스트 관련 데이터베이스 작업을 처리하는 DAO(Data Access Object) 클래스입니다.
 * 코드 테스트 조회, 사용자 코드 제출, AI 분석 결과 업데이트 등의 기능을 제공합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>코드 테스트 목록 조회</li>
 *   <li>조건별 필터링된 코드 테스트 목록 조회</li>
 *   <li>특정 코드 테스트 상세 정보 조회</li>
 *   <li>사용자 코드 제출 및 결과 저장</li>
 *   <li>AI 분석 정보 업데이트</li>
 * </ul>
 */
public class CodeTestDAO {

	/**
	 * 데이터베이스 연결 객체
	 */
	private Connection conn;

	/**
	 * 기본 생성자입니다.
	 * JNDI를 통해 데이터베이스 커넥션 풀에서 연결을 가져옵니다.
	 */
	public CodeTestDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 직접 데이터베이스에 연결하는 생성자입니다.
	 * 커넥션 풀을 사용하지 않고 직접 데이터베이스에 연결합니다.
	 *
	 * @param directConnection true인 경우 직접 연결을 시도합니다.
	 */
	public CodeTestDAO(boolean directConnection) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 환경에 맞게 수정
			String user = "devmap";
			String pw = "java1234";

			conn = DriverManager.getConnection(url, user, pw);
			System.out.println("✅ [CodeTestDAO] 직접 연결 성공");

		} catch (Exception e) {
			System.out.println("❌ [CodeTestDAO] 직접 연결 실패");
			e.printStackTrace();
		}
	}

	/**
	 * 기본 코드 테스트 목록을 조회합니다.
	 * 테스트 유형이 '기본'인 모든 코드 테스트를 조회합니다.
	 *
	 * @return 코드 테스트 목록을 담은 List 객체
	 */
	public List<CodeTestDTO> getCodeTestList() {
		List<CodeTestDTO> list = new ArrayList<>();

		try {
			String sql = "SELECT c.codeTestSeq, c.codeTestTitle, c.difficulty, s.subjectName " +
					"FROM tblCodeTest c " +
					"INNER JOIN tblSubject s ON c.subjectSeq = s.subjectSeq " +
					"WHERE c.codeTestType = '기본' " +
					"ORDER BY c.codeTestSeq";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CodeTestDTO dto = new CodeTestDTO();
				dto.setCodeTestSeq(rs.getInt("codeTestSeq"));
				dto.setCodeTestTitle(rs.getString("codeTestTitle"));
				dto.setDifficulty(rs.getInt("difficulty"));
				dto.setSubjectName(rs.getString("subjectName"));
				list.add(dto);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 사용자 정의 조건에 따라 필터링된 코드 테스트 목록을 조회합니다.
	 * 해결 여부, 난이도, 기술 카테고리 등의 조건으로 필터링 가능합니다.
	 *
	 * @param userSeq 사용자 시퀀스
	 * @param status 해결 상태 ("solved", "unsolved", null)
	 * @param difficulty 난이도 (문자열 형태의 숫자)
	 * @param techSeq 기술 카테고리 시퀀스
	 * @return 필터링된 코드 테스트 목록을 담은 List 객체
	 */
	public List<CodeTestDTO> getFilteredList(int userSeq, String status, String difficulty, String techSeq) {
		List<CodeTestDTO> list = new ArrayList<>();

		try {
			StringBuilder sql = new StringBuilder("SELECT DISTINCT c.codeTestSeq, c.codeTestTitle, c.difficulty, "
					+ "s.subjectName, t.techName, "
					+ "(SELECT COUNT(*) FROM tblUserCoteResult r WHERE r.codeTestSeq = c.codeTestSeq AND r.isPassed = 'Y') AS solvedCount, "
					+ "(SELECT COUNT(*) FROM tblUserCoteResult r WHERE r.codeTestSeq = c.codeTestSeq) AS totalCount, "
					+ "(SELECT MAX(r.isPassed) FROM tblUserCoteResult r WHERE r.userSeq = ? AND r.codeTestSeq = c.codeTestSeq) AS isPassed, "
					+ "(SELECT COUNT(*) FROM tblUserBookmark b WHERE b.userSeq = ? AND b.codeTestSeq = c.codeTestSeq) AS isBookmarked "
					+ "FROM tblCodeTest c " + "JOIN tblSubject s ON c.subjectSeq = s.subjectSeq "
					+ "JOIN tblTech t ON s.techSeq = t.techSeq " + "WHERE 1=1 AND c.codeTestType = '기본' ");

			// 동적 조건 추가
			if (status != null && !status.isEmpty()) {
				if (status.equals("solved")) {
					sql.append(
							" AND EXISTS (SELECT 1 FROM tblUserCoteResult r WHERE r.userSeq = ? AND r.codeTestSeq = c.codeTestSeq AND r.isPassed = 'Y') ");
				} else if (status.equals("unsolved")) {
					sql.append(
							" AND NOT EXISTS (SELECT 1 FROM tblUserCoteResult r WHERE r.userSeq = ? AND r.codeTestSeq = c.codeTestSeq AND r.isPassed = 'Y') ");
				}
			}

			if (difficulty != null && !difficulty.equals("")) {
				sql.append(" AND c.difficulty = ?");
			}

			if (techSeq != null && !techSeq.isEmpty()) {
				sql.append(" AND t.techSeq = ?");
			}

			sql.append(" ORDER BY c.codeTestSeq");

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			int idx = 1;
			pstmt.setInt(idx++, userSeq); // 첫 번째 ? = 분석용 subquery의 userSeq
			pstmt.setInt(idx++, userSeq); // isBookmarked

			if (status != null && !status.isEmpty()) {
				pstmt.setInt(idx++, userSeq); // EXISTS / NOT EXISTS 안의 userSeq
			}

			if (difficulty != null && !difficulty.equals("")) {
				pstmt.setInt(idx++, Integer.parseInt(difficulty));
			}

			if (techSeq != null && !techSeq.isEmpty()) {
				pstmt.setInt(idx++, Integer.parseInt(techSeq));
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CodeTestDTO dto = new CodeTestDTO();
				dto.setCodeTestSeq(rs.getInt("codeTestSeq"));
				dto.setCodeTestTitle(rs.getString("codeTestTitle"));
				dto.setDifficulty(rs.getInt("difficulty"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setTechName(rs.getString("techName"));
//                dto.setStatus(rs.getString("status"));
				dto.setSolvedCount(rs.getInt("solvedCount"));
				dto.setTotalCount(rs.getInt("totalCount"));
				dto.setIsPassed(rs.getString("isPassed"));
				list.add(dto);

				// 정답률 계산 (0으로 나누는 것 방지)
				int rate = 0;
				if (dto.getTotalCount() > 0) {
					rate = (int) ((dto.getSolvedCount() * 100.0) / dto.getTotalCount());
				}
				dto.setAccuracy(rate);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}



	/**
	 * 사용자가 제출한 코드를 데이터베이스에 저장합니다.
	 * 사용자 코드 제출 결과를 tblUserCoteResult 테이블에 저장하고 생성된 시퀀스 값을 반환합니다.
	 *
	 * @param codeTestSeq 코드 테스트 시퀀스
	 * @return 생성된 사용자 코드 결과 시퀀스 값, 실패 시 -1 반환
	 */
	public CodeTestDTO get(String codeTestSeq) {
		CodeTestDTO dto = null;

		try {
			String sql = "SELECT codeTestSeq, codeTestTitle, codeTestDesc, inputDescription, outputDescription, sampleInput, sampleOutput "
					+ "FROM tblCodeTest WHERE codeTestSeq = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, codeTestSeq);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new CodeTestDTO();
				dto.setCodeTestSeq(rs.getInt("codeTestSeq"));
				dto.setCodeTestTitle(rs.getString("codeTestTitle"));
				dto.setContent(rs.getString("codeTestDesc"));
				dto.setInputDescription(rs.getString("inputDescription"));
				dto.setOutputDescription(rs.getString("outputDescription"));
				dto.setSampleInput(rs.getString("sampleInput"));
				dto.setSampleOutput(rs.getString("sampleOutput"));

				// ✅ 북마크 여부 확인
				String bookmarkSql = "SELECT COUNT(*) FROM tblUserBookmark WHERE userSeq = ? AND codeTestSeq = ?";
				PreparedStatement bmPstmt = conn.prepareStatement(bookmarkSql);
				bmPstmt.setInt(1, 1); // userSeq 임시
				bmPstmt.setString(2, codeTestSeq);
				ResultSet bmRs = bmPstmt.executeQuery();
				if (bmRs.next()) {
					dto.setBookmarked(bmRs.getInt(1) > 0);
				}
				bmRs.close();
				bmPstmt.close();
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}


	/**
	 * 특정 사용자의 특정 코드 테스트에 대한 최신 제출 결과를 조회합니다.
	 * 제출 날짜 기준으로 가장 최근에 제출한 결과를 반환합니다.
	 *
	 * @param userSeq 사용자 시퀀스
	 * @param codeTestSeq 코드 테스트 시퀀스
	 * @return 최신 사용자 코드 테스트 결과 정보를 담은 UserCoteResultDTO 객체, 조회 실패 시 null 반환
	 */
	public int insertUserCode(String codeTestSeq, int userSeq, String code) {
		int userCoteResultSeq = -1;

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
			Connection conn = ds.getConnection();

			String sql = "INSERT INTO tblUserCoteResult (userCoteResultSeq, userSeq, codeTestSeq, submittedCode, submitDate) "
					+ "VALUES (seqTblUserCoteResult.nextVal, ?, ?, ?, SYSDATE)";
			String[] returnCols = { "userCoteResultSeq" };
			PreparedStatement pstmt = conn.prepareStatement(sql, returnCols);
			pstmt.setInt(1, userSeq);
			pstmt.setString(2, codeTestSeq);
			pstmt.setString(3, code);

			int result = pstmt.executeUpdate();

			if (result == 1) {
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					userCoteResultSeq = rs.getInt(1); // ✅ 생성된 시퀀스값 받기
				}
				rs.close();
			}

			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userCoteResultSeq;
	}

	/**
	 * 특정 사용자의 특정 코드 테스트에 대한 최신 제출 결과를 조회합니다.
	 * 제출 날짜 기준으로 가장 최근에 제출한 결과를 반환합니다.
	 *
	 * @param userSeq 사용자 시퀀스
	 * @param codeTestSeq 코드 테스트 시퀀스
	 * @return 최신 사용자 코드 테스트 결과 정보를 담은 UserCoteResultDTO 객체, 조회 실패 시 null 반환
	 */
	public UserCoteResultDTO getLatestUserResult(int userSeq, String codeTestSeq) {
	    UserCoteResultDTO dto = null;

	    try {
	        String sql = "SELECT u.submittedCode, u.aiFeedback, u.weakConcepts, u.isPassed, c.codeTestTitle " +
	                     "FROM tblUserCoteResult u " +
	                     "JOIN tblCodeTest c ON u.codeTestSeq = c.codeTestSeq " +
	                     "WHERE u.userSeq = ? AND u.codeTestSeq = ? " +
	                     "ORDER BY u.submitDate DESC";

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, userSeq);
	        pstmt.setString(2, codeTestSeq);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            dto = new UserCoteResultDTO();
	            dto.setSubmittedCode(rs.getString("submittedCode"));
	            dto.setAiFeedback(rs.getString("aiFeedback"));
	            dto.setWeakConcepts(rs.getString("weakConcepts"));
	            dto.setIsPassed(rs.getString("isPassed"));
	            dto.setCodeTestTitle(rs.getString("codeTestTitle")); // ✅ 제목 가져오기
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return dto;
	}



	/**
	 * AI 분석 정보를 업데이트하고 사용자 레이팅을 반영합니다.
	 * 코드 테스트 결과에 AI 피드백, 취약 개념, 통과 여부, 테스트 케이스 결과 등을 업데이트하고,
	 * 최초 통과 시 사용자 레이팅을 난이도에 따라 증가시킵니다.
	 *
	 * <p>난이도별 레이팅 증가량:</p>
	 * <ul>
	 *   <li>난이도 1: 10점</li>
	 *   <li>난이도 2: 20점</li>
	 *   <li>난이도 3: 30점</li>
	 * </ul>
	 *
	 * @param userCoteResultSeq 사용자 코드 테스트 결과 시퀀스
	 * @param aiFeedback AI가 제공한 피드백
	 * @param weakConcepts 취약한 개념들
	 * @param isPassed 테스트 통과 여부 ('Y' 또는 'N')
	 * @param testCasePassed 통과한 테스트 케이스 수
	 * @param totalTestCases 전체 테스트 케이스 수
	 * @param execTime 실행 시간(초)
	 * @return 업데이트 성공 시 1, 실패 시 0 반환
	 */
	public int updateAnalysisInfo(int userCoteResultSeq, String aiFeedback, String weakConcepts, String isPassed,
	        int testCasePassed, int totalTestCases, double execTime) {

	    int result = 0;

	    try {
	        // 매번 커넥션 새로 열기
	        Context initContext = new InitialContext();
	        Context envContext = (Context)initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource)envContext.lookup("jdbc/pool");
	        conn = ds.getConnection();
	    	
	        System.out.println("🛠 [updateAnalysisInfo] 메서드 진입");
	        System.out.println("🧾 파라미터: userCoteResultSeq = " + userCoteResultSeq);
	        System.out.println("🧾 aiFeedback = " + aiFeedback);
	        System.out.println("🧾 weakConcepts = " + weakConcepts);
	        System.out.println("🧾 isPassed = " + isPassed);

	        // 1. 문제 정보 조회
	        System.out.println("🔍 [1단계] 문제 난이도 + userSeq + codeTestSeq 조회 시작");
	    	
	    	
	        // 1. 문제 난이도 및 codeTestSeq, userSeq 조회
	        String difficultySql = "SELECT c.difficulty, c.codeTestSeq, u.userSeq " +
	                               "FROM tblCodeTest c " +
	                               "JOIN tblUserCoteResult u ON c.codeTestSeq = u.codeTestSeq " +
	                               "WHERE u.userCoteResultSeq = ?";
	        PreparedStatement diffStmt = conn.prepareStatement(difficultySql);
	        diffStmt.setInt(1, userCoteResultSeq);
	        ResultSet diffRs = diffStmt.executeQuery();

	        int difficulty = 1;
	        String codeTestSeq = "";
	        int userSeq = -1;

	        if (diffRs.next()) {
	            difficulty = diffRs.getInt("difficulty");
	            codeTestSeq = diffRs.getString("codeTestSeq");
	            userSeq = diffRs.getInt("userSeq");
	        }
	        System.out.println("✅ [1단계] 조회 완료: difficulty = " + difficulty + ", codeTestSeq = " + codeTestSeq + ", userSeq = " + userSeq);
	        
	        
	        diffRs.close();
	        diffStmt.close();

	        // 2. 난이도 → 점수
	        int ratingChange = switch (difficulty) {
	            case 2 -> 20;
	            case 3 -> 30;
	            default -> 10;
	        };
	        System.out.println("🎯 [2단계] ratingChange 계산됨: " + ratingChange);
	        
	        
	        // 3. 최초 통과 여부 확인
	        boolean isFirstPassed = false;
	        if ("Y".equals(isPassed)) {
	        	System.out.println("🔍 [3단계] 최초 통과 여부 확인 중...");
	        	
	            String checkSql = "SELECT COUNT(*) FROM tblUserCoteResult " +
	                              "WHERE userSeq = ? AND codeTestSeq = ? AND isPassed = 'Y' AND isRated = 'Y'";
	            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
	            checkStmt.setInt(1, userSeq);
	            checkStmt.setString(2, codeTestSeq);
	            ResultSet checkRs = checkStmt.executeQuery();
	            if (checkRs.next() && checkRs.getInt(1) == 0) {
	                isFirstPassed = true;
	            }
	            System.out.println("🧠 최초 통과 여부: " + isFirstPassed);
	            
	            checkRs.close();
	            checkStmt.close();
	        }

	        // 4. UPDATE 실행
	        System.out.println("📝 [4단계] UPDATE 실행 시작");
	        
	        String updateSql = "UPDATE tblUserCoteResult SET " +
	                           "aiFeedback = ?, weakConcepts = ?, isPassed = ?, " +
	                           "testCasePassed = ?, totalTestCases = ?, executionTime = ?, " +
	                           "ratingChange = ?, isRated = ? " +
	                           "WHERE userCoteResultSeq = ?";
	        
	        System.out.println("📌 [업데이트 직전] userCoteResultSeq: " + userCoteResultSeq);
	        System.out.println("📌 isPassed: " + isPassed + ", isFirstPassed: " + isFirstPassed);
	        System.out.println("📌 aiFeedback: " + aiFeedback);
	        System.out.println("📌 weakConcepts: " + weakConcepts);
	        System.out.println("📌 ratingChange: " + ratingChange);
	        
	        
	        
	        
	        PreparedStatement pstmt = conn.prepareStatement(updateSql);
	        pstmt.setString(1, aiFeedback);
	        pstmt.setString(2, weakConcepts);
	        pstmt.setString(3, isPassed);
	        pstmt.setInt(4, testCasePassed);
	        pstmt.setInt(5, totalTestCases);
	        pstmt.setDouble(6, execTime);
	        pstmt.setInt(7, ratingChange);
	        pstmt.setString(8, isFirstPassed ? "Y" : "N");
	        pstmt.setInt(9, userCoteResultSeq);
	        result = pstmt.executeUpdate();
	        pstmt.close();
	        System.out.println("✅ [4단계] UPDATE 완료, result = " + result);
	        
	        // 5. 유저 레이팅 반영 (최초 통과일 경우)
	        if (isFirstPassed) {
	        	
	        	
	        	
	        	System.out.println("📈 [5단계] 유저 레이팅 반영 시작");
	        	
	            String checkRatingSql = "SELECT COUNT(*) FROM tblUserRating WHERE userSeq = ?";
	            PreparedStatement checkRatingStmt = conn.prepareStatement(checkRatingSql);
	            checkRatingStmt.setInt(1, userSeq);
	            ResultSet ratingRs = checkRatingStmt.executeQuery();

	            boolean hasRating = false;
	            if (ratingRs.next() && ratingRs.getInt(1) > 0) {
	                hasRating = true;
	            }
	            ratingRs.close();
	            checkRatingStmt.close();

	            if (hasRating) {
	                String updateRatingSql = "UPDATE tblUserRating SET " +
	                                         "currentRating = currentRating + ?, " +
	                                         "totalSolved = totalSolved + 1, " +
	                                         "lastUpdated = SYSDATE " +
	                                         "WHERE userSeq = ?";
	                
	                PreparedStatement updateStmt = conn.prepareStatement(updateRatingSql);
	                System.out.println("📊 [레이팅 변경 감지] userSeq: " + userSeq + ", 변경점수: " + ratingChange);
	                System.out.println("📊 [변경 이유] 코드 테스트 통과 - 문제번호: " + codeTestSeq);
	                updateStmt.setInt(1, ratingChange);
	                updateStmt.setInt(2, userSeq);
	                updateStmt.executeUpdate();
	                updateStmt.close();
	                
	                insertRatingHistory(userSeq, ratingChange, "코드 테스트 통과");
	                
	                System.out.println("✅ [5단계] 기존 유저 → 레이팅 UPDATE 완료");

	            } else {
	            	// 신규 유저 → insert
	            	String insertRatingSql = "INSERT INTO tblUserRating (userSeq, totalSolved, currentRating, lastUpdated) " +
	            	                         "VALUES (?, 1, ?, SYSDATE)";
	            	PreparedStatement insertStmt = conn.prepareStatement(insertRatingSql);
	            	insertStmt.setInt(1, userSeq);
	            	insertStmt.setInt(2, 1000 + ratingChange); // 초기 + 점수
	            	insertStmt.executeUpdate();
	            	insertStmt.close();

	            	// ✅ 히스토리 기록 추가
	            	insertRatingHistory(userSeq, ratingChange, "코드 테스트 통과");
	            }
	        }

	        conn.close();
	        System.out.println("[DAO] 분석 결과 저장 완료 (userCoteResultSeq 기반): " + result);

	    } catch (Exception e) {
	    	System.out.println("❗[updateAnalysisInfo] 예외 발생: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return result;
	}




	/**
	 * 사용자의 북마크 상태를 토글합니다.
	 * 이미 북마크된 경우 북마크를 제거하고, 북마크되지 않은 경우 북마크를 추가합니다.
	 *
	 * @param userSeq 사용자 일련번호
	 * @param codeTestSeq 코드테스트 일련번호
	 * @return 토글 후 북마크 상태 (true: 북마크됨, false: 북마크 안됨)
	 */
	public boolean toggleBookmark(int userSeq, int codeTestSeq) {
		boolean isNowBookmarked = false;

		try {
			String checkSql = "SELECT COUNT(*) FROM tblUserBookmark WHERE userSeq = ? AND codeTestSeq = ?";
			PreparedStatement checkPstmt = conn.prepareStatement(checkSql);
			checkPstmt.setInt(1, userSeq);
			checkPstmt.setInt(2, codeTestSeq);
			ResultSet rs = checkPstmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				// 이미 북마크됨 → 삭제
				String deleteSql = "DELETE FROM tblUserBookmark WHERE userSeq = ? AND codeTestSeq = ?";
				PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);
				deletePstmt.setInt(1, userSeq);
				deletePstmt.setInt(2, codeTestSeq);
				deletePstmt.executeUpdate();
				isNowBookmarked = false;
			} else {
				// 북마크 안됨 → 추가
				String insertSql = "INSERT INTO tblUserBookmark (bookmarkSeq, userSeq, codeTestSeq) VALUES (seqTblUserBookmark.nextVal, ?, ?)";
				PreparedStatement insertPstmt = conn.prepareStatement(insertSql);
				insertPstmt.setInt(1, userSeq);
				insertPstmt.setInt(2, codeTestSeq);
				insertPstmt.executeUpdate();
				isNowBookmarked = true;
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isNowBookmarked;
	}



	/**
	 * 특정 코드 테스트에 대한 모든 테스트 케이스를 조회합니다.
	 *
	 * @param codeTestSeq 코드테스트 일련번호
	 * @return 테스트 케이스 목록
	 */
	public List<TestCaseDTO> getTestCases(String codeTestSeq) {
		List<TestCaseDTO> list = new ArrayList<>();

		try {
			String sql = "SELECT testCaseSeq, codeTestSeq, input, expectedOutput "
					+ "FROM tblCodeTestCase WHERE codeTestSeq = ? ORDER BY testCaseSeq";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, codeTestSeq);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				TestCaseDTO dto = new TestCaseDTO();
				dto.setTestCaseSeq(rs.getInt("testCaseSeq"));
				dto.setCodeTestSeq(rs.getInt("codeTestSeq"));
				dto.setInput(rs.getString("input"));
				dto.setOutput(rs.getString("expectedOutput"));

				list.add(dto);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}



	/**
	 * 사용자가 특정 과목에 대한 복습 이력이 있는지 확인합니다.
	 *
	 * @param userSeq 사용자 일련번호
	 * @param subjectSeq 과목 일련번호
	 * @return 복습 이력 존재 여부
	 */
	public boolean hasSubjectReview(int userSeq, int subjectSeq) {
		boolean exists = false;

		try {
			String sql = "SELECT COUNT(*) FROM tblSubjectReview WHERE userSeq = ? AND subjectSeq = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userSeq);
			pstmt.setInt(2, subjectSeq);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				exists = rs.getInt(1) > 0;
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exists;
	}



	/**
	 * 사용자의 과목 복습 이력을 생성하거나 업데이트합니다.
	 * 기존 이력이 없으면 새로 추가하고, 있으면 최근 복습일과 기억력 점수를 업데이트합니다.
	 * 기억력 점수는 30점 증가하며, 최대 100점입니다.
	 *
	 * @param userSeq 사용자 일련번호
	 * @param subjectSeq 과목 일련번호
	 */
	public void insertOrUpdateSubjectReview(int userSeq, int subjectSeq) {
		try {
			// 🔑 커넥션 새로 열기
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
			Connection newConn = ds.getConnection();

			System.out.println("📝 [복습이력] 시작 - userSeq=" + userSeq + ", subjectSeq=" + subjectSeq);

			String selectSql = "SELECT memoryScore FROM tblSubjectReview WHERE userSeq = ? AND subjectSeq = ?";
			PreparedStatement selectStmt = newConn.prepareStatement(selectSql);
			selectStmt.setInt(1, userSeq);
			selectStmt.setInt(2, subjectSeq);
			ResultSet rs = selectStmt.executeQuery();

			if (rs.next()) {
				// ✅ 기존 이력 있음 → update
				int prevScore = rs.getInt("memoryScore");
				int newScore = Math.min(100, prevScore + 30); // 기억력 증가

				String updateSql = "UPDATE tblSubjectReview SET memoryScore = ?, lastReviewDate = SYSDATE, isComplete = ? WHERE userSeq = ? AND subjectSeq = ?";
				PreparedStatement updateStmt = newConn.prepareStatement(updateSql);
				updateStmt.setInt(1, newScore);
				updateStmt.setString(2, newScore >= 100 ? "Y" : "N");
				updateStmt.setInt(3, userSeq);
				updateStmt.setInt(4, subjectSeq);
				updateStmt.executeUpdate();
				updateStmt.close();

				System.out.println("✅ 복습 이력 업데이트 완료");

			} else {
				// ✅ 이력 없음 → insert
				String insertSql = "INSERT INTO tblSubjectReview (reviewSeq, userSeq, subjectSeq, lastReviewDate, memoryScore, isComplete) "
						+ "VALUES (seqTblSubjectReview.nextVal, ?, ?, SYSDATE, ?, 'N')";
				PreparedStatement insertStmt = newConn.prepareStatement(insertSql);
				insertStmt.setInt(1, userSeq);
				insertStmt.setInt(2, subjectSeq);
				insertStmt.setInt(3, 100); // 초기 기억력 100점
				insertStmt.executeUpdate();
				insertStmt.close();

				System.out.println("✅ 복습 이력 신규 추가 완료");
			}

			rs.close();
			selectStmt.close();
			newConn.close();

		} catch (Exception e) {
			System.out.println("❌ 복습 이력 INSERT/UPDATE 실패: " + e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * 코드 테스트 일련번호로 해당 테스트가 속한 과목의 일련번호를 조회합니다.
	 *
	 * @param codeTestSeq 코드테스트 일련번호
	 * @return 과목 일련번호, 조회 실패 시 -1 반환
	 */
	public int getSubjectSeqByCodeTest(String codeTestSeq) {
		int subjectSeq = -1;

		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
			Connection newConn = ds.getConnection();

			String sql = "SELECT subjectSeq FROM tblCodeTest WHERE codeTestSeq = ?";
			PreparedStatement pstmt = newConn.prepareStatement(sql);
			pstmt.setString(1, codeTestSeq);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				subjectSeq = rs.getInt("subjectSeq");
				System.out.println("📌 가져온 subjectSeq: " + subjectSeq);
			} else {
				System.out.println("❌ subjectSeq 못 찾음 → codeTestSeq: " + codeTestSeq);
			}

			rs.close();
			pstmt.close();
			newConn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return subjectSeq;
	}



	/**
	 * 코드 테스트가 기본 문제인지 확인합니다.
	 * codeTestType이 '기본'인 경우 true를 반환합니다.
	 *
	 * @param codeTestSeq 코드테스트 일련번호
	 * @return 기본 문제 여부
	 */
	public boolean isBasicProblem(String codeTestSeq) {
		boolean isBasic = false;

		try {
			// 🔥 커넥션 새로 열기 (기존 필드 conn 사용 X)
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
			Connection newConn = ds.getConnection();

			String sql = "SELECT codeTestType FROM tblCodeTest WHERE codeTestSeq = ?";
			PreparedStatement pstmt = newConn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(codeTestSeq));
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String type = rs.getString("codeTestType");
				System.out.println("📌 codeTestType in DB: [" + type + "]");
				isBasic = "기본".equals(type.trim());
			} else {
				System.out.println("❌ rs.next() == false → codeTestSeq [" + codeTestSeq + "] 조회 실패!");
			}

			rs.close();
			pstmt.close();
			newConn.close(); // 이 커넥션만 닫기

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isBasic;
	}



	/**
	 * 특정 과목에 속한 모든 복습 문제를 조회합니다.
	 *
	 * @param subjectSeq 과목 일련번호
	 * @return 복습 문제 목록
	 */
	public List<CodeTestDTO> getReviewCodeTests(int subjectSeq) {
		List<CodeTestDTO> list = new ArrayList<>();

		try {
			String sql = "SELECT codeTestSeq, codeTestTitle, difficulty " + "FROM tblCodeTest "
					+ "WHERE subjectSeq = ? AND codeTestType = '복습' " + "ORDER BY codeTestSeq"; // 또는 난이도/등록일 등 정렬 기준 추가
																								// 가능

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, subjectSeq);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CodeTestDTO dto = new CodeTestDTO();
				dto.setCodeTestSeq(rs.getInt("codeTestSeq"));
				dto.setCodeTestTitle(rs.getString("codeTestTitle"));
				dto.setDifficulty(rs.getInt("difficulty"));

				list.add(dto);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}


	/**
	 * 사용자별 맞춤 복습 문제를 추천합니다.
	 * 기억력 점수가 낮은 과목부터 우선순위를 두어, 아직 해결하지 않은 복습 문제를 추천합니다.
	 *
	 * @param userSeq 사용자 일련번호
	 * @return 추천된 복습 문제 목록
	 */
	public List<CodeTestDTO> getRecommendedReviewProblems(int userSeq) {
		List<CodeTestDTO> result = new ArrayList<>();

		try {
			// 1. 기억력 낮은 순으로 복습 필요 과목 조회
			String subjectSql = "SELECT subjectSeq FROM tblSubjectReview " + "WHERE userSeq = ? AND isComplete = 'N' "
					+ "ORDER BY memoryScore ASC";

			PreparedStatement subjectStmt = conn.prepareStatement(subjectSql);
			subjectStmt.setInt(1, userSeq);
			ResultSet subjectRs = subjectStmt.executeQuery();

			while (subjectRs.next()) {
				int subjectSeq = subjectRs.getInt("subjectSeq");

				// 2. 해당 과목의 복습 문제 중 아직 안 푼 것 1개 조회
				String codeSql = "SELECT * FROM ("
						+ "  SELECT c.codeTestSeq, c.codeTestTitle, c.difficulty, s.subjectName "
						+ "  FROM tblCodeTest c " + "  JOIN tblSubject s ON c.subjectSeq = s.subjectSeq "
						+ "  WHERE c.subjectSeq = ? AND c.codeTestType = '복습' "
						+ "    AND NOT EXISTS (SELECT 1 FROM tblUserCoteResult r WHERE r.userSeq = ? AND r.codeTestSeq = c.codeTestSeq AND r.isPassed = 'Y') "
						+ "  ORDER BY c.codeTestSeq" + ") WHERE ROWNUM = 1";

				PreparedStatement codeStmt = conn.prepareStatement(codeSql);
				codeStmt.setInt(1, subjectSeq);
				codeStmt.setInt(2, userSeq);
				ResultSet codeRs = codeStmt.executeQuery();

				if (codeRs.next()) {
					CodeTestDTO dto = new CodeTestDTO();
					dto.setCodeTestSeq(codeRs.getInt("codeTestSeq"));
					dto.setCodeTestTitle(codeRs.getString("codeTestTitle"));
					dto.setDifficulty(codeRs.getInt("difficulty"));
					dto.setSubjectName(codeRs.getString("subjectName"));
					result.add(dto); // ✅ 추천 문제 리스트에 추가
				}

				codeRs.close();
				codeStmt.close();
			}

			subjectRs.close();
			subjectStmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 망각 곡선 이론에 따라 모든 사용자의 과목별 기억력 점수를 감소시킵니다.
	 * 마지막 복습일로부터 경과한 시간에 비례하여 기억력이 지수적으로 감소합니다.
	 * 완료 상태(isComplete='Y')인 항목은 처리하지 않습니다.
	 */
	public void decayMemoryScores() {
	    try {
	        String sql = "SELECT userSeq, subjectSeq, lastReviewDate, memoryScore FROM tblSubjectReview WHERE isComplete = 'N'";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int userSeq = rs.getInt("userSeq");
	            int subjectSeq = rs.getInt("subjectSeq");
	            Date lastReviewDate = rs.getDate("lastReviewDate");
	            int prevScore = rs.getInt("memoryScore");

	            long millisElapsed = System.currentTimeMillis() - lastReviewDate.getTime();
	            long daysElapsed = millisElapsed / (1000 * 60 * 60 * 24);

	            double lambda = 0.15;
	            int newScore = (int) (100 * Math.exp(-lambda * daysElapsed));
	            newScore = Math.max(newScore, 0);

	            System.out.printf("🧠 [%d-%d] %.1f일 경과, 점수 %d → %d\n",
	                    userSeq, subjectSeq, (double) daysElapsed, prevScore, newScore);

	            String updateSql = "UPDATE tblSubjectReview SET memoryScore = ? WHERE userSeq = ? AND subjectSeq = ?";
	            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
	            updateStmt.setInt(1, newScore);
	            updateStmt.setInt(2, userSeq);
	            updateStmt.setInt(3, subjectSeq);
	            updateStmt.executeUpdate();
	            updateStmt.close();
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();

	        System.out.println("📉 기억력 감소 반영 완료");

	    } catch (Exception e) {
	        System.out.println("❌ [decayMemoryScores] 예외 발생");
	        e.printStackTrace();
	    }
	}




	/**
	 * 사용자의 레이팅 변경 내역을 기록합니다.
	 *
	 * @param userSeq 사용자 일련번호
	 * @param rating 변경된 레이팅 점수
	 * @param reason 레이팅 변경 사유
	 */
	public void insertRatingHistory(int userSeq, int rating, String reason) {
	    try {
	        // 커넥션 다시 열기 (필드 conn이 닫혔을 수도 있으니 안전하게 처리)
	        Context initContext = new InitialContext();
	        Context envContext = (Context) initContext.lookup("java:/comp/env");
	        DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
	        Connection newConn = ds.getConnection();

	        String sql = "INSERT INTO tblUserRatingHistory (ratingHistorySeq, userSeq, rating, reason, changedAt) " +
	                     "VALUES (seqTblUserRatingHistory.nextVal, ?, ?, ?, SYSDATE)";
	        PreparedStatement pstmt = newConn.prepareStatement(sql);
	        pstmt.setInt(1, userSeq);
	        pstmt.setInt(2, rating);
	        pstmt.setString(3, reason);

	        pstmt.executeUpdate();
	        pstmt.close();
	        newConn.close();

	        System.out.println("✅ 레이팅 히스토리 기록 완료: " + rating + "점, 이유: " + reason);

	    } catch (Exception e) {
	        System.out.println("❌ 레이팅 히스토리 기록 실패");
	        e.printStackTrace();
	    }
	}
	
	
	
	

}
