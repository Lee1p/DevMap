package com.project.java.devtest.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DevFieldDAO {
    
    private DataSource dataSource;
    
    public DevFieldDAO() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool");
        } catch (Exception e) {
            System.err.println("JNDI DataSource lookup 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 모든 개발 분야를 가져오는 메서드
    public List<DevFieldDTO> getAllDevFields() {
        List<DevFieldDTO> devFields = new ArrayList<>();
        String sql = "SELECT fieldSeq, fieldName FROM tblDeveloperField";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();  // 커넥션 풀에서 연결 가져오기
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                DevFieldDTO devFieldDTO = new DevFieldDTO();
                devFieldDTO.setFieldSeq(rs.getInt("fieldSeq"));
                devFieldDTO.setFieldName(rs.getString("fieldName"));
                devFields.add(devFieldDTO);
            }

        } catch (SQLException e) {
            System.err.println("개발 분야 목록 조회 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 자원 해제: 순서대로 닫기
            try {
                if (rs != null) rs.close();  // ResultSet 닫기
                if (stmt != null) stmt.close();  // PreparedStatement 닫기
                if (conn != null) conn.close();  // Connection 닫기
            } catch (SQLException e) {
                System.err.println("자원 해제 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return devFields;
    }
    
 // DevFieldDAO에 추가할 메서드
    public DevFieldDTO getDevFieldById(int fieldSeq) {
        DevFieldDTO devField = null;
        String sql = "SELECT fieldSeq, fieldName FROM tblDeveloperField WHERE fieldSeq = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, fieldSeq);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    devField = new DevFieldDTO();
                    devField.setFieldSeq(rs.getInt("fieldSeq"));
                    devField.setFieldName(rs.getString("fieldName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("개발 분야 조회 실패 (ID: " + fieldSeq + "): " + e.getMessage());
            e.printStackTrace();
        }
        
        return devField;
    }

	public DevFieldDTO getDevFieldById() {
		return null;
	}
}
