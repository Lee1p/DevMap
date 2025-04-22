package com.project.java.roadmap.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 기술(Tech) 관련 DB 작업을 수행하는 DAO 클래스입니다.
 *
 * 주요 기능:
 * - 기술 상세 조회
 * - 기술명 또는 기술번호 기반 조회
 * - 기술에 속한 과목 리스트 조회
 * - 분야 기반 로드맵 조회
 */
public class TechDAO {

    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    /**
     * 생성자 - 커넥션 풀을 통해 DB 연결을 초기화합니다.
     */
    public TechDAO() {
        try {
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            DataSource ds = (DataSource) env.lookup("jdbc/pool");
            conn = ds.getConnection();
            stat = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 내부 리소스를 안전하게 종료합니다.
     */
    private void close() {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pstat != null) pstat.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (stat != null) stat.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * 기술 번호를 기반으로 기술 정보를 조회합니다.
     *
     * @param techSeq 기술 번호
     * @return TechDTO 객체, 조회 실패 시 null
     */
    public TechDTO getTech(int techSeq) {
        TechDTO dto = null;
        try {
            String sql = "SELECT techSeq, techName, techDesc FROM tblTech WHERE techSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, techSeq);
            rs = pstat.executeQuery();

            if (rs.next()) {
                dto = new TechDTO();
                dto.setTechSeq(rs.getInt("techSeq"));
                dto.setTechName(rs.getString("techName"));
                dto.setTechDesc(rs.getString("techDesc"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return dto;
    }

    /**
     * 기술 번호에 연결된 과목 목록을 조회합니다.
     *
     * @param techSeq 기술 번호
     * @return SubjectDTO 리스트
     */
    public List<SubjectDTO> getSubjectsByTechSeq(int techSeq) {
        List<SubjectDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT subjectSeq, subjectName, subjectDifficulty FROM tblSubject WHERE techSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, techSeq);
            rs = pstat.executeQuery();

            while (rs.next()) {
                SubjectDTO dto = new SubjectDTO();
                dto.setSubjectSeq(rs.getInt("subjectSeq"));
                dto.setSubjectName(rs.getString("subjectName"));
                dto.setSubjectDifficulty(rs.getString("subjectDifficulty"));
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
     * 기술명을 기반으로 기술 정보를 조회합니다.
     * 대소문자 및 공백을 무시하고 검색합니다.
     *
     * @param techName 기술명
     * @return TechDTO 객체, 없으면 null
     */
    public TechDTO getTechByName(String techName) {
        TechDTO dto = null;
        try {
            String sql = "SELECT techSeq, techName, techDesc FROM tblTech WHERE LOWER(TRIM(techName)) = LOWER(TRIM(?))";
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, techName);
            rs = pstat.executeQuery();

            if (rs.next()) {
                dto = new TechDTO();
                dto.setTechSeq(rs.getInt("techSeq"));
                dto.setTechName(rs.getString("techName"));
                dto.setTechDesc(rs.getString("techDesc"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return dto;
    }

    /**
     * 기술 번호로 기술 정보를 조회합니다.
     * categorySeq 필드까지 포함한 전체 조회입니다.
     *
     * @param techSeq 기술 번호
     * @return TechDTO 객체
     */
    public TechDTO getTechBySeq(int techSeq) {
        TechDTO dto = null;
        try {
            String sql = "SELECT * FROM tblTech WHERE techSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, techSeq);
            rs = pstat.executeQuery();

            if (rs.next()) {
                dto = new TechDTO();
                dto.setTechSeq(rs.getInt("techSeq"));
                dto.setTechName(rs.getString("techName"));
                dto.setCategorySeq(rs.getInt("categorySeq"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return dto;
    }

    /**
     * 분야 번호에 해당하는 기술 로드맵 리스트를 조회합니다.
     *
     * @param fieldSeq 분야 번호
     * @return TechDTO 리스트
     */
    public List<TechDTO> getRoadmapListByField(int fieldSeq) {
        List<TechDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT techSeq, techName, techDesc FROM tblTech WHERE fieldSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, fieldSeq);
            rs = pstat.executeQuery();

            while (rs.next()) {
                TechDTO dto = new TechDTO();
                dto.setTechSeq(rs.getInt("techSeq"));
                dto.setTechName(rs.getString("techName"));
                dto.setTechDesc(rs.getString("techDesc"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }
}
