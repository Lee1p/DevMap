package com.project.java.roadmap.model;

import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 사용자-기술(UserTech) 매핑 정보를 처리하는 DAO 클래스입니다.
 *
 * 주요 기능:
 * - 기술 완료 여부 확인
 * - 사용자 기술 완료 정보 등록
 */
public class UserTechDAO {

    private Connection conn;
    private PreparedStatement pstat;
    private ResultSet rs;

    /**
     * 생성자 - 커넥션 풀을 통해 DB 연결을 초기화합니다.
     */
    public UserTechDAO() {
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
     * 사용자가 특정 기술을 완료했는지 여부를 확인합니다.
     *
     * @param userSeq 사용자 ID
     * @param techSeq 기술 ID
     * @return true: 완료 정보 존재, false: 미완료
     */
    public boolean exists(int userSeq, int techSeq) {
        String sql = "SELECT COUNT(*) FROM tblUserTech WHERE userSeq = ? AND techSeq = ?";
        try {
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            pstat.setInt(2, techSeq);
            rs = pstat.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 사용자 기술 완료 정보를 등록합니다.
     *
     * @param userSeq 사용자 ID
     * @param techSeq 기술 ID
     */
    public void insert(int userSeq, int techSeq) {
        String sql = "INSERT INTO tblUserTech (userTechSeq, techSeq, userSeq) VALUES (seqUserTech.NEXTVAL, ?, ?)";
        try {
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, techSeq);
            pstat.setInt(2, userSeq);
            pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 리소스를 안전하게 정리합니다.
     */
    public void close() {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pstat != null) pstat.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
}
