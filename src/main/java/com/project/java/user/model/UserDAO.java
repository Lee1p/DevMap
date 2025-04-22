// src/main/java/com/project/java/user/model/UserDAO.java
package com.project.java.user.model;

import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.mindrot.jbcrypt.BCrypt;

import com.project.java.user.model.UserDTO;
/**
 * 로그인/회원가입 등 유저 정보를 처리하는 DAO입니다.
 */
public class UserDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    // 데이터베이스 연결
    private Connection getConnection() throws Exception {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/pool");
        return ds.getConnection();
    }

    // 자원 해제
    private void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 이메일 중복 확인
    /**
     * 이메일 중복 여부를 확인하는 메서드입니다.
     * @param email
     * @return
     */
    public boolean isEmailExists(String email) {
        boolean exists = false;
        try {
            conn = getConnection();
            String sql = "SELECT COUNT(*) FROM tblUser WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return exists;
    }
    
    

    // 사용자 등록 (일반 회원가입)
//    public int registerUser(UserDTO user) {
//        int result = 0;
//        try {
//            conn = getConnection();
//            String sql = "INSERT INTO tblUser (user_id, email, password, name, phone_number, role, join_date, is_activate) VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, 'USER', SYSTIMESTAMP, 'Y')";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, user.getEmail());
//            pstmt.setString(2, user.getPassword()); // 암호화된 비밀번호
//            pstmt.setString(3, user.getName());
//            pstmt.setString(4, user.getPhoneNumber());
//            result = pstmt.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close();
//        }
//        return result;
//    }
    
//    public int registerUser(UserDTO user) {
//        int result = 0;
//        try {
//            conn = getConnection();
//            //String sql = "INSERT INTO tblUser (userSeq, social_type, email, password, name, phone_number, role, join_date, is_active) VALUES (tblUser_seq.NEXTVAL, 'local', ?, ?, ?, ?, 'user', SYSTIMESTAMP, 'Y')";
//            String sql = "INSERT INTO tblUser (userSeq, social_type, email, password, name, role, join_date, is_active) VALUES (tblUser_seq.NEXTVAL, 'local', ?, ?, ?, 'user', SYSTIMESTAMP, 'Y')";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, user.getEmail());
//            pstmt.setString(2, user.getPassword());
//            pstmt.setString(3, user.getName());
//            //pstmt.setString(4, user.getPhoneNumber());
//            
//            result = pstmt.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error in registerUser: " + e.getMessage());
//        } finally {
//            close();
//        }
//        return result;
//    }
    
    /**
     * 사용자 등록 (일반 회원가입)하는 메서드입니다.
     * @param user
     * @return
     */
    	// 사용자 등록 (일반 회원가입)
		// registerUser 메서드 수정
	    public int registerUser(UserDTO user) {
	        int result = 0;
	        try {
	            conn = getConnection();
	            String sql = "INSERT INTO tblUser (social_type, email, password, name, role, join_date, is_active) VALUES ('local', ?, ?, ?, 'user', SYSTIMESTAMP, 'Y')";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, user.getEmail());
	            pstmt.setString(2, user.getPassword());
	            pstmt.setString(3, user.getName());
	            result = pstmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error in registerUser: " + e.getMessage());
	        } finally {
	            close();
	        }
	        return result;
	    }


    

    // 로그인 검증
//    public UserDTO loginUser(String email, String password) {
//        UserDTO user = null;
//        try {
//            conn = getConnection();
//            String sql = "SELECT * FROM tblUser WHERE email = ? AND is_active = 'Y'";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, email);
//            rs = pstmt.executeQuery();
//            
//            if (rs.next()) {
//                String dbPassword = rs.getString("password");
//                // 비밀번호 검증 (BCrypt 사용)
//                if (BCrypt.checkpw(password, dbPassword)) {
//                    user = new UserDTO();
//                    //user.setUserId(rs.getInt("user_id"));
//                    user.setEmail(rs.getString("email"));
//                    user.setName(rs.getString("name"));
//                    //user.setPhoneNumber(rs.getString("phone_number"));
//                    user.setProfileImage(rs.getString("profile_image"));
//                    user.setRole(rs.getString("role"));
//                    //user.setSocialId(rs.getString("social_id"));
//                    user.setSocialType(rs.getString("social_type"));
//                    user.setUserSeq(rs.getInt("userSeq"));
//                    
//                    // 마지막 로그인 시간 업데이트
//                    updateLastLoginDate(user.getUserSeq());
//                    
//                    System.out.println("userSeq:" + user.getUserSeq());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close();
//        }
//        return user;
//    }
	    
	    /**
	     * 로그인을 검증하는 메서드입니다.
	     * @param email
	     * @param password
	     * @return
	     */
	    // 로그인 검증
	    // loginUser 메서드 수정
	    public UserDTO loginUser(String email, String password) {
	        UserDTO user = null;
	        try {
	            conn = getConnection();
	            String sql = "SELECT * FROM tblUser WHERE email = ? AND is_active = 'Y'";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, email);
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                String dbPassword = rs.getString("password");
	                if (BCrypt.checkpw(password, dbPassword)) {
	                    user = new UserDTO();
	                    user.setUserSeq(rs.getInt("userSeq"));
	                    user.setEmail(rs.getString("email"));
	                    user.setName(rs.getString("name"));
	                    user.setProfileImage(rs.getString("profile_image"));
	                    user.setRole(rs.getString("role"));
	                    user.setSocialType(rs.getString("social_type"));
	                    updateLastLoginDate(user.getUserSeq());
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            close();
	        }
	        return user;
	    }



    // 소셜 로그인 사용자 처리
//    public UserDTO processSocialLogin(String socialId, String socialType, String email, String name, String profileImage) {
//        UserDTO user = null;
//        try {
//            conn = getConnection();
//            
//            // 1. 소셜 ID로 사용자 찾기
//            String sql = "SELECT * FROM tblUser WHERE social_id = ? AND social_type = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, socialId);
//            pstmt.setString(2, socialType);
//            rs = pstmt.executeQuery();
//            
//            if (rs.next()) {
//                // 기존 소셜 로그인 사용자
//                user = new UserDTO();
//                user.setUserId(rs.getInt("user_id"));
//                user.setSocialId(rs.getString("social_id"));
//                user.setSocialType(rs.getString("social_type"));
//                user.setEmail(rs.getString("email"));
//                user.setName(rs.getString("name"));
//                user.setPhoneNumber(rs.getString("phone_number"));
//                user.setProfileImage(rs.getString("profile_image"));
//                user.setRole(rs.getString("role"));
//                
//                // 마지막 로그인 시간 업데이트
//                updateLastLoginDate(user.getUserId());
//            } else {
//                // 2. 이메일로 사용자 찾기 (이미 가입한 사용자일 수 있음)
//                sql = "SELECT * FROM tblUser WHERE email = ?";
//                pstmt = conn.prepareStatement(sql);
//                pstmt.setString(1, email);
//                rs = pstmt.executeQuery();
//                
//                if (rs.next()) {
//                    // 이메일로 가입된 사용자가 있으면 소셜 정보 연결
//                    int userId = rs.getInt("user_id");
//                    sql = "UPDATE tblUser SET social_id = ?, social_type = ?, mod_date = SYSTIMESTAMP WHERE user_id = ?";
//                    pstmt = conn.prepareStatement(sql);
//                    pstmt.setString(1, socialId);
//                    pstmt.setString(2, socialType);
//                    pstmt.setInt(3, userId);
//                    pstmt.executeUpdate();
//                    
//                    // 업데이트된 사용자 정보 가져오기
//                    user = getUserById(userId);
//                } else {
//                    // 3. 새 사용자 등록
//                    sql = "INSERT INTO tblUser (user_id, social_id, social_type, email, name, profile_image, role, join_date, is_activate) "
//                         + "VALUES (USER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, 'USER', SYSTIMESTAMP, 'Y')";
//                    pstmt = conn.prepareStatement(sql);
//                    pstmt.setString(1, socialId);
//                    pstmt.setString(2, socialType);
//                    pstmt.setString(3, email);
//                    pstmt.setString(4, name);
//                    pstmt.setString(5, profileImage);
//                    
//                    if (pstmt.executeUpdate() > 0) {
//                        // 새로 등록된 사용자 정보 가져오기
//                        sql = "SELECT * FROM tblUser WHERE social_id = ? AND social_type = ?";
//                        pstmt = conn.prepareStatement(sql);
//                        pstmt.setString(1, socialId);
//                        pstmt.setString(2, socialType);
//                        rs = pstmt.executeQuery();
//                        
//                        if (rs.next()) {
//                            user = new UserDTO();
//                            user.setUserId(rs.getInt("user_id"));
//                            user.setSocialId(rs.getString("social_id"));
//                            user.setSocialType(rs.getString("social_type"));
//                            user.setEmail(rs.getString("email"));
//                            user.setName(rs.getString("name"));
//                            user.setProfileImage(rs.getString("profile_image"));
//                            user.setRole(rs.getString("role"));
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            close();
//        }
//        return user;
//    }
	    
	    // 소셜 로그인 사용자 처리
	 // processSocialLogin 메서드 수정
//	    public UserDTO processSocialLogin(String socialType, String email, String name, String profileImage) {
//	        UserDTO user = null;
//	        try {
//	            conn = getConnection();
//	            String sql = "SELECT * FROM tblUser WHERE email = ? AND social_type = ?";
//	            pstmt = conn.prepareStatement(sql);
//	            pstmt.setString(1, email);
//	            pstmt.setString(2, socialType);
//	            rs = pstmt.executeQuery();
//	            if (rs.next()) {
//	                user = new UserDTO();
//	                user.setUserSeq(rs.getInt("userSeq"));
//	                user.setSocialType(rs.getString("social_type"));
//	                user.setEmail(rs.getString("email"));
//	                user.setName(rs.getString("name"));
//	                user.setProfileImage(rs.getString("profile_image"));
//	                user.setRole(rs.getString("role"));
//	                updateLastLoginDate(user.getUserSeq());
//	            } else {
//	                sql = "INSERT INTO tblUser (social_type, email, name, profile_image, role, join_date, is_active) VALUES (?, ?, ?, ?, 'user', SYSTIMESTAMP, 'Y')";
//	                pstmt = conn.prepareStatement(sql, new String[]{"userSeq"});
//	                pstmt.setString(1, socialType);
//	                pstmt.setString(2, email);
//	                pstmt.setString(3, name);
//	                pstmt.setString(4, profileImage);
//	                if (pstmt.executeUpdate() > 0) {
//	                    rs = pstmt.getGeneratedKeys();
//	                    if (rs.next()) {
//	                        int userSeq = rs.getInt(1);
//	                        user = new UserDTO();
//	                        user.setUserSeq(userSeq);
//	                        user.setSocialType(socialType);
//	                        user.setEmail(email);
//	                        user.setName(name);
//	                        user.setProfileImage(profileImage);
//	                        user.setRole("user");
//	                    }
//	                }
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        } finally {
//	            close();
//	        }
//	        return user;
//	    }

	    
	    /**
	     * 마지막 로그인 시간을 업데이트하는 메서드입니다.
	     * @param userSeq
	     */
	    // 마지막 로그인 시간 업데이트
		// updateLastLoginDate 메서드 수정
	    private void updateLastLoginDate(int userSeq) {
	        try {
	            String sql = "UPDATE tblUser SET last_login_date = SYSTIMESTAMP WHERE userSeq = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, userSeq);
	            pstmt.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


    // ID로 사용자 조회
//    private UserDTO getUserById(int userId) {
//        UserDTO user = null;
//        try {
//            String sql = "SELECT * FROM tblUser WHERE user_id = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, userId);
//            rs = pstmt.executeQuery();
//            
//            if (rs.next()) {
//                user = new UserDTO();
//                user.setUserId(rs.getInt("user_id"));
//                user.setSocialId(rs.getString("social_id"));
//                user.setSocialType(rs.getString("social_type"));
//                user.setEmail(rs.getString("email"));
//                user.setName(rs.getString("name"));
//                user.setPhoneNumber(rs.getString("phone_number"));
//                user.setProfileImage(rs.getString("profile_image"));
//                user.setRole(rs.getString("role"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return user;
//    }
	    
	    /**
	     * 유저 정보를 가져오는 메서드입니다.
	     * @param userSeq
	     * @return
	     */
	    private UserDTO getUserById(int userSeq) {
	        UserDTO user = null;
	        try {
	            String sql = "SELECT * FROM tblUser WHERE userSeq = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, userSeq);
	            rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	                user = new UserDTO();
	                user.setUserSeq(rs.getInt("userSeq"));
	                user.setSocialType(rs.getString("social_type"));
	                user.setEmail(rs.getString("email"));
	                user.setName(rs.getString("name"));
	                user.setProfileImage(rs.getString("profile_image"));
	                user.setRole(rs.getString("role"));
	                // phone_number 컬럼이 테이블 마지막에 추가되었으므로 존재하는지 확인
	                if (columnExists(rs, "phone_number")) {
	                    user.setPhoneNumber(rs.getString("phone_number"));
	                }
	                user.setIs_active(rs.getString("is_active"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return user;
	    }

	    // 컬럼 존재 여부 확인 헬퍼 메서드
	    private boolean columnExists(ResultSet rs, String columnName) {
	        try {
	            rs.findColumn(columnName);
	            return true;
	        } catch (SQLException e) {
	            return false;
	        }
	    }
  
	 
	    /**
	     * 데이터베이스에 인증코드를 저장하는 메서드입니다.
	     * @param email
	     * @param code
	     * @param expireTime
	     */
    
    // 인증 코드 저장
    public void saveVerificationCode(String email, String code, Timestamp expireTime) {
        try {
            conn = getConnection();
            
            // 기존 코드가 있으면 삭제
            String deleteSql = "DELETE FROM email_verification WHERE email = ?";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            
            // 새 코드 저장
            String sql = "INSERT INTO email_verification (email, code, expire_time, verified) VALUES (?, ?, ?, 'N')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, code);
            pstmt.setTimestamp(3, expireTime);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 수신 받은 인증코드와 사용자가 기입한 인증코드의 일치여부를 확인하는 메서드입니다.
     * @param email
     * @param code
     * @return
     */
    // 인증 코드 확인
    public boolean verifyCode(String email, String code) {
        boolean verified = false;
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM email_verification WHERE email = ? AND code = ? AND expire_time > SYSTIMESTAMP";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, code);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // 인증 상태 업데이트
                String updateSql = "UPDATE email_verification SET verified = 'Y' WHERE email = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, email);
                pstmt.executeUpdate();
                
                verified = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        
        return verified;
    }

    /**
     * 비밀번호를 재설정하는 메서드입니다.
     * @param email
     * @param token
     * @param expireTime
     */
 // 비밀번호 재설정 토큰 저장
    public void savePasswordResetToken(String email, String token, Timestamp expireTime) {
        try {
            conn = getConnection();
            // 기존 토큰이 있으면 삭제
            String deleteSql = "DELETE FROM password_reset_tokens WHERE email = ?";
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            
            // 새 토큰 저장
            String sql = "INSERT INTO password_reset_tokens (email, token, expire_time) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, token);
            pstmt.setTimestamp(3, expireTime);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * 비밀번호 재설정 시, 토큰의 유효성을 검사합니다.
     * @param email
     * @param token
     * @return
     */
    // 비밀번호 재설정 토큰 유효성 검사
    public boolean isValidPasswordResetToken(String email, String token) {
        boolean isValid = false;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM password_reset_tokens WHERE email = ? AND token = ? AND expire_time > SYSTIMESTAMP";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, token);
            rs = pstmt.executeQuery();
            isValid = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return isValid;
    }
    
    /**
     * 비밀번호 업데이트를 제공하는 메서드입니다.
     * @param email
     * @param hashedPassword
     * @return
     */
    // 비밀번호 업데이트
    public boolean updatePassword(String email, String hashedPassword) {
        boolean updated = false;
        try {
            conn = getConnection();
            String sql = "UPDATE tblUser SET password = ?, mod_date = SYSTIMESTAMP WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, email);
            updated = pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return updated;
    }

    // 비밀번호 재설정 토큰 삭제
    public void deletePasswordResetToken(String email) {
        try {
            conn = getConnection();
            String sql = "DELETE FROM password_reset_tokens WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    
    /**
     * 유저의 정보를 가져옵니다.
     * @param userSeq
     * @return
     */
    public UserDTO selectUser(int userSeq) {
        String sql = "SELECT * FROM tblUser WHERE userSeq = ?"; 

        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userSeq);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserSeq(rs.getInt("userSeq")); 
                user.setSocialType(rs.getString("social_type"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password")); 
                user.setName(rs.getString("name"));
                user.setProfileImage(rs.getString("profile_image"));  
                user.setRole(rs.getString("role"));
                user.setJoinDate(rs.getTimestamp("join_date")); 
                user.setModDate(rs.getTimestamp("mod_date")); 
                user.setLastLoginDate(rs.getTimestamp("last_login_date")); 
                user.setIs_active(rs.getString("is_active")); 
                user.setPhoneNumber(rs.getString("phone_number")); 
                user.setDevtest(rs.getString("devtest"));

                return user;
            } else {
                System.out.println("⚠️ userSeq: " + userSeq + " 에 해당하는 사용자가 없음.");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ SQL 오류: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("⚠️ 기타 오류: " + e.getMessage());
        }
        return null;
    }
}

