package com.project.java.test.codetest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

/**
 * 학습 주제(Subject) 관련 데이터베이스 작업을 처리하는 DAO(Data Access Object) 클래스입니다.
 * 주제 목록 조회 및 복습이 필요한 주제 선정 등의 기능을 제공합니다.
 *
 * <p>이 클래스는 JNDI를 통해 데이터베이스 커넥션 풀에서 연결을 획득하여 사용합니다.</p>
 */
public class SubjectDAO {

    /** 데이터베이스 연결 객체 */
    private Connection conn;

    /**
     * 기본 생성자입니다.
     * JNDI를 통해 데이터베이스 커넥션 풀에서 연결을 가져옵니다.
     */
    public SubjectDAO() {
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/pool");
            this.conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 모든 학습 주제 이름을 조회합니다.
     *
     * @return 모든 학습 주제 이름을 담은 List 객체
     */
    public List<String> getAllSubjectNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT subjectName FROM tblSubject";

        try (
                PreparedStatement pstat = conn.prepareStatement(sql);
                ResultSet rs = pstat.executeQuery();
        ) {
            while (rs.next()) {
                list.add(rs.getString("subjectName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 사용자에게 다음으로 복습이 필요한 주제를 선정합니다.
     * 다음 조건을 만족하는 주제 중 기억력 점수가 가장 낮은 주제를 반환합니다:
     * <ul>
     *   <li>아직 완료되지 않은 주제(isComplete = 'N')</li>
     *   <li>마지막 복습 일자가 3일 이상 지난 주제</li>
     * </ul>
     *
     * @param userSeq 사용자 시퀀스
     * @return 다음 복습이 필요한 주제의 시퀀스, 해당하는 주제가 없으면 null 반환
     */
    public Integer getNextReviewSubject(int userSeq) {
        Integer subjectSeq = null;

        try {
            String sql = "SELECT subjectSeq " +
                    "FROM tblSubjectReview " +
                    "WHERE userSeq = ? " +
                    "AND isComplete = 'N' " +
                    "AND lastReviewDate <= SYSDATE - 3 " +
                    "ORDER BY memoryScore ASC FETCH FIRST 1 ROWS ONLY";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userSeq);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                subjectSeq = rs.getInt("subjectSeq");
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjectSeq;
    }
}