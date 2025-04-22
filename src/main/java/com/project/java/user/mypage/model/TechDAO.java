package com.project.java.user.mypage.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 사용자가 현재 수강하는 언어에 대한 정보를 처리하는 DAO입니다.
 */
public class TechDAO {

    private Connection conn;

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
     * 사용자가 로드맵으로부터 제공받은 언어 리스트를 출력합니다.
     * @return
     */
    public TechDTO getTechList() {

        try {
        	
        	//현재 수강하는 거 한개만 가져오기
        	
            String sql = "SELECT techSeq, techName FROM tblTech ORDER BY techSeq";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                TechDTO tech = new TechDTO();
                tech.setTechSeq(rs.getInt("techSeq"));
                tech.setTechName(rs.getString("techName"));
                
                return tech;
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
