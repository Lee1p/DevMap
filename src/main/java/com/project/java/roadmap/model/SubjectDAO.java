package com.project.java.roadmap.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *  과목(Subject) 관련 DB 처리를 담당하는 DAO 클래스
 *
 * 주요 기능:
 * - 과목 상세 조회
 * - 과목에 연결된 자료(Resource) 조회
 * - 사용자별 완료 과목 확인/등록
 * - 리소스 종료 관리
 */
public class SubjectDAO {

    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    /**
     *  생성자 - 커넥션 풀에서 DB 연결 획득
     */
    public SubjectDAO() {
        try {
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            DataSource ds = (DataSource) env.lookup("jdbc/pool");

            conn = ds.getConnection();
            stat = conn.createStatement();

        } catch (Exception e) {
            System.out.println("❌ DB 연결 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *  기본 리소스 종료 메서드
     */
    public void close() {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pstat != null) pstat.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     *  명시적으로 모든 리소스 종료
     */
    public void close(Connection conn, PreparedStatement pstat, ResultSet rs, Statement stat) {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pstat != null) pstat.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (stat != null) stat.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     *  과목 상세 정보 조회
     *
     * @param subjectSeq 과목 번호
     * @return SubjectDTO (과목 정보 + 자료 리스트), 실패 시 null
     */
    public SubjectDTO getSubjectDetail(int subjectSeq) {
        try {
            String sql = "SELECT subjectSeq, subjectName, subjectDesc, subjectDifficulty "
                       + "FROM tblSubject WHERE subjectSeq = ? AND delDate IS NULL";

            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, subjectSeq);
            rs = pstat.executeQuery();

            if (rs.next()) {
                SubjectDTO dto = new SubjectDTO();

                // ✅ 과목 정보 매핑
                dto.setSubjectSeq(rs.getInt("subjectSeq"));
                dto.setSubjectName(rs.getString("subjectName"));
                dto.setSubjectDesc(rs.getString("subjectDesc"));
                dto.setSubjectDifficulty(rs.getString("subjectDifficulty"));

                // ✅ 관련 자료 조회
                dto.setResources(getResourcesBySubject(subjectSeq));

                return dto;
            }

        } catch (Exception e) {
            System.out.println("❌ getSubjectDetail 예외: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(conn, pstat, rs, stat);
        }

        return null;
    }

    /**
     *  과목 번호로 자료 리스트 조회
     *
     * @param subjectSeq 과목 번호
     * @return 자료 리스트
     */
    private List<ResourceDTO> getResourcesBySubject(int subjectSeq) {
        List<ResourceDTO> list = new ArrayList<>();
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT resourceTitle, resourceUrl, resourceType FROM tblResource "
                       + "WHERE subjectSeq = ? AND delDate IS NULL AND isActive = 'Y'";

            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, subjectSeq);
            rs = pstat.executeQuery();

            while (rs.next()) {
                ResourceDTO res = new ResourceDTO();
                res.setResourceTitle(rs.getString("resourceTitle"));
                res.setResourceUrl(rs.getString("resourceUrl"));
                res.setResourceType(rs.getString("resourceType"));
                list.add(res);
            }

        } catch (Exception e) {
            System.out.println("❌ getResourcesBySubject 예외: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstat != null) pstat.close(); } catch (Exception e) {}
        }

        return list;
    }

    /**
     *  기술 번호에 해당하는 과목 목록 조회
     *
     * @param techSeq 기술 번호
     * @return 과목 리스트
     */
    public List<SubjectDTO> getSubjectsByTech(int techSeq) {
        List<SubjectDTO> list = new ArrayList<>();
        String sql = "SELECT subjectSeq, subjectName, subjectDifficulty FROM tblSubject WHERE techSeq = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, techSeq);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SubjectDTO dto = new SubjectDTO();
                    dto.setSubjectSeq(rs.getInt("subjectSeq"));
                    dto.setSubjectName(rs.getString("subjectName"));
                    dto.setSubjectDifficulty(rs.getString("subjectDifficulty"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ getSubjectsByTech 예외: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    /**
     *  단일 과목 정보 조회
     *
     * @param subjectSeq 과목 번호
     * @return SubjectDTO (이름, 설명, 난이도)
     */
    public SubjectDTO getSubject(int subjectSeq) {
        SubjectDTO dto = null;
        try {
            String sql = "SELECT subjectName, subjectDesc, subjectDifficulty FROM tblSubject WHERE subjectSeq = ?";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setInt(1, subjectSeq);
            ResultSet rs = pstat.executeQuery();

            if (rs.next()) {
                dto = new SubjectDTO();
                dto.setSubjectName(rs.getString("subjectName"));
                dto.setSubjectDesc(rs.getString("subjectDesc"));
                dto.setSubjectDifficulty(rs.getString("subjectDifficulty"));
            }

            rs.close();
            pstat.close();
        } catch (Exception e) {
            System.out.println("❌ getSubject 예외: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstat != null) pstat.close(); } catch (Exception e) {}
        }

        return dto;
    }

    /**
     *  과목 이름으로 과목 목록 조회
     *
     * @param subjectName 과목명
     * @return 일치하는 과목 리스트
     */
    public List<SubjectDTO> getSubjectsByName(String subjectName) {
        List<SubjectDTO> list = new ArrayList<>();

        try {
            String sql = "SELECT subjectSeq, subjectName, subjectDifficulty FROM tblSubject WHERE subjectName = ?";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1, subjectName);
            ResultSet rs = pstat.executeQuery();

            while (rs.next()) {
                SubjectDTO dto = new SubjectDTO();
                dto.setSubjectSeq(rs.getInt("subjectSeq"));
                dto.setSubjectName(rs.getString("subjectName"));
                dto.setSubjectDifficulty(rs.getString("subjectDifficulty"));
                list.add(dto);
            }

            rs.close();
            pstat.close();
        } catch (Exception e) {
            System.out.println("❌ getSubjectsByName 예외: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstat != null) pstat.close(); } catch (Exception e) {}
        }

        return list;
    }

    /**
     *  사용자의 과목 완료 정보 등록
     *
     * @param userSeq 사용자 번호
     * @param subjectSeq 과목 번호
     */
    public void insertCompletedSubject(int userSeq, int subjectSeq) {
        try {
            String sql = "INSERT INTO tblUserSubject (userSubjectSeq, userSeq, subjectSeq, endDate) "
                       + "VALUES (seqUserSubject.NEXTVAL, ?, ?, SYSDATE)";

            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            pstat.setInt(2, subjectSeq);

            int result = pstat.executeUpdate(); // ✅ 결과는 필요 시 사용
        } catch (Exception e) {
            System.out.println("❌ insertCompletedSubject 예외: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstat != null) pstat.close(); } catch (Exception e) {}
            try { if (stat != null) stat.close(); } catch (Exception e) {}
        }
    }

    /**
     *  사용자의 과목 endDate 갱신
     *
     * @param userSeq 사용자 번호
     * @param subjectSeq 과목 번호
     */
    public void updateEndDate(int userSeq, int subjectSeq) {
        try {
            String sql = "UPDATE tblUserSubject SET endDate = SYSDATE WHERE userSeq = ? AND subjectSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            pstat.setInt(2, subjectSeq);
            pstat.executeUpdate();
        } catch (Exception e) {
            System.out.println("❌ updateEndDate 예외: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstat != null) pstat.close(); } catch (Exception e) {}
            try { if (stat != null) stat.close(); } catch (Exception e) {}
        }
    }

    /**
     *  커넥션 종료 (외부에서 직접 닫을 때 사용)
     */
    public void closeConnection() {
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     *  사용자가 완료한 과목 목록 조회
     *
     * @param userSeq 사용자 번호
     * @return 완료된 subjectSeq 리스트
     */
    public List<Integer> getCompletedSubjectsByUser(int userSeq) {
        List<Integer> list = new ArrayList<>();
        try {
            String sql = "SELECT subjectSeq FROM tblUserSubject WHERE userSeq = ? AND endDate IS NOT NULL";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            rs = pstat.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("subjectSeq"));
            }

        } catch (Exception e) {
            System.out.println("❌ getCompletedSubjectsByUser 예외: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close();
        }

        return list;
    }
}
