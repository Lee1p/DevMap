package com.project.java.user.mypage.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.project.java.badge.model.BadgeDTO;

/**
 * Mypage와 관련된 정보를 처리하는 DAO입니다.
 * <p>토큰 값을 넘겨 유저 객체에서 사용자의 정보만을 가져옵니다.</p>
 * <p>토큰 값을 넘겨 뱃지 리스트에서 사용자가 획득한 뱃지의 정보만을 가져옵니다.</p>
 * <p>토큰 값을 넘겨 랭킹 객체에서 사용자가 얻은 랭킹 점수 정보만을 가져옵니다.</p>
 * 
 */
public class MypageDAO {
    
    private DataSource ds;

    public MypageDAO() {
        try {
            Context ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            ds = (DataSource) env.lookup("jdbc/pool");

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
    
    /**
     * 먼저 유저테이블에서 로그인한 유저 정보만 가져옵니다.
     * @param userSeq 사용자 고유 번호
     * @return 
     */
    public MypageDTO getUser(int userSeq) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            String sql = "SELECT * FROM tblUser WHERE userSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            rs = pstat.executeQuery();

            if (rs.next()) {
                MypageDTO result = new MypageDTO();
                result.setUserSeq(rs.getInt("userSeq"));
                result.setSocial_type(rs.getString("social_type"));
                result.setEmail(rs.getString("email"));
                result.setPassword(rs.getString("password"));
                result.setName(rs.getString("name"));
                result.setPhone_number(rs.getString("phone_number"));
                result.setProfile_image(rs.getString("profile_image"));
                result.setRole(rs.getString("role"));
                result.setJoin_date(rs.getString("join_date"));
                result.setMod_date(rs.getString("mod_date"));
                result.setLast_login_date(rs.getString("last_login_date"));
                result.setIs_active(rs.getString("is_active"));
                result.setUser_level(rs.getString("user_level"));
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstat, rs);
        }
        return null;
    }
    
    /**
     * 사용자가 획득한 뱃지의 정보를 가져옵니다.
     * @param userSeq 사용자 고유 번호
     * @return 뱃지 리스트
     */
    public ArrayList<BadgeDTO> getBadge(int userSeq) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            String sql = "SELECT * FROM tblbadge b INNER JOIN tbluserbadge ub ON b.badgeseq = ub.badgeseq WHERE ub.userSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            rs = pstat.executeQuery();

            ArrayList<BadgeDTO> list = new ArrayList<>();
            while (rs.next()) {
                BadgeDTO result = new BadgeDTO();
                result.setBadgeSeq(rs.getString("badgeSeq"));
                result.setBadgeType(rs.getString("badgeType"));
                result.setBadgeName(rs.getString("badgeName"));
                result.setBadgeDesc(rs.getString("badgeDesc"));
                result.setBadgeIcon(rs.getString("badgeIcon"));
                result.setBadgeCondition(rs.getString("badgeCondition"));
                result.setIsActive(rs.getString("isActive"));
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
    
    /**
     * 현재 사용자의 랭킹 점수를 가져오는 메서드입니다.
     * @param userSeq
     * @return 랭킹 점수 제공
     */
    public ArrayList<UserLinkDTO> getUserLink(int userSeq) {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();
            String sql = "select * from tblUserLink where userSeq = ?";
            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            
            rs = pstat.executeQuery();
            
            ArrayList<UserLinkDTO> list = new ArrayList<UserLinkDTO>();
            
            while (rs.next()) {
            	UserLinkDTO result = new UserLinkDTO();
            	
   
                result.setUserLinkSeq(rs.getString("userSeq"));
                result.setUserSeq(rs.getInt("userSeq"));
                result.setLinkName(rs.getString("LinkName"));
                result.setLinkType(rs.getString("LinkType"));
                result.setRegDate(rs.getString("regDate"));
                result.setModDate(rs.getString("modDate"));
                
                
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
