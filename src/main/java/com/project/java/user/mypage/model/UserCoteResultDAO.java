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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 많이 틀린 문제를 출력하는 DAO입니다.
 * 틀린 횟수를 카운팅하여, 많이 틀린 순으로 리스트에 출력하는 DAO입니다.
 */
@Getter
@Setter
@ToString
public class UserCoteResultDAO {

	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private DataSource ds;


	public UserCoteResultDAO() {
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
            if (conn != null) conn.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /*
     * 유저의 정보를 받아, 틀린 횟수와 테스트의 정보를 출력합니다.
     * 많이 틀린 순대로 리스트로 받환합니다.
     */
	public ArrayList<UserCoteResultDTO> getCoteResult(int userSeq) {
		try {

			String sql = "select count(*) as cnt, r.codetestseq as codetestseq, c.codetesttitle from TBLusercoteresult r\n"
					+ "    inner join tblcodetest c\n"
					+ "       on r.codetestseq = c.codetestseq\n"
					+ "        where userseq = ? and ispassed = 'N'\n"
					+ "            group by r.codetestseq, c.codetesttitle\n"
					+ "                order by cnt desc";

			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, userSeq);

			rs = pstat.executeQuery();
			
			ArrayList<UserCoteResultDTO> list = new ArrayList<UserCoteResultDTO>();

			while(rs.next()) {
				UserCoteResultDTO result = new UserCoteResultDTO();
				
				result.setCnt(rs.getString("cnt"));
				result.setCodetestSeq(rs.getString("codetestSeq"));
				result.setCodetesttitle(rs.getString("codetesttitle"));
				
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
