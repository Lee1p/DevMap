package com.project.java.user.mypage.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 출결 정보를 가져오는 DAO입니다.
 */
public class AttendanceDAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private DataSource ds;

	
	
	public AttendanceDAO() {
		try {
			Context ctx = new InitialContext();
			Context env = (Context)ctx.lookup("java:comp/env");
			DataSource ds = (DataSource)env.lookup("jdbc/pool");

			conn = ds.getConnection();
			stat = conn.createStatement();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 리소스 닫기 (공통 메서드)
    public void close(Connection conn, PreparedStatement pstat, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstat != null) pstat.close();
            if (conn != null) conn.close();  // 커넥션 풀에 반환됨
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	//리스트에서 받아오기
    /**
     * 사용자의 출결 테이터를 리스트로 받아오는 메서드입니다.
     * @param userSeq 유저의 번호
     * @return 리스트로 반환
     */
	public ArrayList<AttendanceDTO> getAttendance(int userSeq) {
		try {

			String sql = "select * from tblAttendance\r\n"
					+ "    where userseq = ?";

			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, userSeq);

			rs = pstat.executeQuery();
			
			ArrayList<AttendanceDTO> list = new ArrayList<AttendanceDTO>();

			while(rs.next()) {
				AttendanceDTO result = new AttendanceDTO();
				
				
				result.setAttendanceSeq(rs.getString("attendanceSeq"));
				result.setUserSeq(rs.getInt("userSeq"));
				result.setAttendanceDate(rs.getString("attendanceDate"));
				result.setCountNum(rs.getString("countNum"));


				list.add(result);
			}
			
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            close(conn, pstat, rs);
        }
		return null;
	}

}
