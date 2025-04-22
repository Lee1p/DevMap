package com.project.java.test.codetest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 기술(Tech) 관련 데이터베이스 작업을 처리하는 DAO(Data Access Object) 클래스입니다.
 * 기술 목록 조회 등의 기능을 제공합니다.
 *
 * <p>이 클래스는 JNDI를 통해 데이터베이스 커넥션 풀에서 연결을 획득하여 사용합니다.</p>
 */
public class TechDAO {

    /** 데이터베이스 연결 객체 */
    private Connection conn;

    /**
     * 기본 생성자입니다.
     * JNDI를 통해 데이터베이스 커넥션 풀에서 연결을 가져옵니다.
     */
    public TechDAO() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/pool");
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 모든 기술 목록을 조회합니다.
     * 기술 일련번호(techSeq) 기준으로 정렬된 목록을 반환합니다.
     *
     * @return 기술 목록을 담은 List&lt;TechDTO&gt; 객체
     */
    public List<TechDTO> getTechList() {
        List<TechDTO> list = new ArrayList<>();

        try {
            String sql = "SELECT techSeq, techName FROM tblTech ORDER BY techSeq";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                TechDTO dto = new TechDTO();
                dto.setTechSeq(rs.getInt("techSeq"));
                dto.setTechName(rs.getString("techName"));
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
}