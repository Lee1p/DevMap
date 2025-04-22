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
 * 사용자가 북마크한 객체들을 받아오는 DAO입니다.
 * 마이페이지에서 북마크한 리스트를 조회할 때 사용됩니다.
 */
public class BookMarkDAO {
    private Connection conn;
    private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    public BookMarkDAO() {
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

    public void close() {
        try {
            if (rs != null) rs.close();
            if (pstat != null) pstat.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 리스트에서 받아오기
    /**
     * 북마크 리스트를 받아오는 메서드입니다.
     * @param userSeq 사용자 고유 번호
     * @return 리스트
     */
    public ArrayList<BookMarkDTO> getBookMark(int userSeq) {
        ArrayList<BookMarkDTO> list = new ArrayList<>();

        try {
            String sql = "SELECT b.bookmarkseq, b.userseq, b.codetestseq, b.bookmarkedat, "
                    + "s.subjectname AS subjectname, c.codetesttitle AS codetesttitle "
                    + "FROM tblUserBookmark b "
                    + "INNER JOIN tblCodeTest c ON b.codetestseq = c.codetestseq "
                    + "INNER JOIN tblsubject s ON c.subjectseq = s.subjectseq "
                    + "WHERE userseq = ?";

            pstat = conn.prepareStatement(sql);
            pstat.setInt(1, userSeq);
            rs = pstat.executeQuery();

            while (rs.next()) {
                BookMarkDTO result = new BookMarkDTO();
                result.setBookmarkSeq(rs.getString("bookmarkSeq"));
                result.setUserSeq(rs.getInt("userSeq"));
                result.setCodeTestSeq(rs.getString("codeTestSeq"));
                result.setBookmarkedAt(rs.getString("bookmarkedAt"));
                result.setSubjectName(rs.getString("subjectName"));
                result.setCodetesttitle(rs.getString("codetesttitle"));

                list.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstat != null) pstat.close();
                stat.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
