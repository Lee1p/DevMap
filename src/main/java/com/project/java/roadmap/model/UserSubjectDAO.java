package com.project.java.roadmap.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 사용자 과목 완료 정보를 처리하는 DAO 클래스입니다.
 *
 * 주요 기능:
 * - 과목 완료 여부 확인
 * - 과목 완료 등록 및 갱신
 * - 사용자별 완료 과목 목록 조회
 */
public class UserSubjectDAO {

    private Connection conn;
    private PreparedStatement pstat;
    private ResultSet rs;

    /**
     * 생성자 - 커넥션 풀을 통해 DB 연결을 초기화합니다.
     */
    public UserSubjectDAO() {
        try {
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            DataSource ds = (DataSource) env.lookup("jdbc/pool");
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 사용자가 특정 과목을 이미 완료했는지 여부를 확인합니다.
     *
     * @param userSeq 사용자 ID
     * @param subjectSeq 과목 ID
     * @return true: 이미 완료함, false: 미완료
     */
    public boolean checkIfExists(int userSeq, int subjectSeq) {
        String sql = "SELECT COUNT(*) FROM tblUserSubject WHERE userSeq = ? AND subjectSeq = ?";
        try (PreparedStatement pstat = conn.prepareStatement(sql)) {
            pstat.setInt(1, userSeq);
            pstat.setInt(2, subjectSeq);
            try (ResultSet rs = pstat.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 사용자의 과목 완료 정보를 등록합니다.
     *
     * @param userSeq 사용자 ID
     * @param subjectSeq 과목 ID
     */
    public void insertCompletedSubject(int userSeq, int subjectSeq) {
        try {
            String sql = "INSERT INTO tblUserSubject (userSubjectSeq, userSeq, subjectSeq, endDate) " +
                         "VALUES (seqUserSubject.NEXTVAL, ?, ?, SYSDATE)";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            pstat.setInt(2, subjectSeq);
            pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 사용자의 과목 완료 날짜(endDate)를 갱신합니다.
     *
     * @param userSeq 사용자 ID
     * @param subjectSeq 과목 ID
     * @return 업데이트된 행 수 (0이면 실패)
     */
    public int updateEndDate(int userSeq, int subjectSeq) {
        try {
            String sql = "UPDATE tblUserSubject SET endDate = SYSDATE " +
                         "WHERE userSeq = ? AND subjectSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            pstat.setInt(2, subjectSeq);
            return pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 사용자가 완료한 과목의 subjectSeq 리스트를 반환합니다.
     *
     * @param userSeq 사용자 ID
     * @return 완료한 과목 번호 리스트
     */
    public List<Integer> getCompletedSubjectSeqs(int userSeq) {
        List<Integer> list = new ArrayList<>();
        try {
            String sql = "SELECT subjectSeq FROM tblUserSubject WHERE userSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            rs = pstat.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("subjectSeq"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * DB 및 리소스를 안전하게 종료합니다.
     */
    public void close() {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pstat != null) pstat.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
}
