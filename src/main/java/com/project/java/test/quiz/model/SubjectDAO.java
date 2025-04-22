package com.project.java.test.quiz.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * 과목 정보를 처리하는 DAO입니다.
 */
public class SubjectDAO {
    private Connection conn;

    public SubjectDAO() {
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/pool");
            this.conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 과목별 퀴즈의 리스트를 가져옵니다.
     * @param techSeq 과목 정보
     * @param userSeq 유저 seq
     * @return
     */
    public List<SubjectQuizDTO> getSubjectQuizList(int techSeq, int userSeq) {
           List<SubjectQuizDTO> list = new ArrayList<>();
           String sql = "SELECT s.subjectSeq, s.subjectName, \n"
           		+ "(SELECT COUNT(*) FROM tblQuiz q WHERE q.subjectSeq = s.subjectSeq) as totalQuestions,\n"
           		+ "(SELECT COUNT(DISTINCT r.quizSeq) FROM tblUserQuizResult r JOIN tblQuiz q ON r.quizSeq = q.quizSeq \n"
           		+ "WHERE q.subjectSeq = s.subjectSeq AND r.userSeq = ? AND r.isCorrect = 'Y') as correctAnswers, \n"
           		+ "(SELECT COUNT(DISTINCT q.quizSeq) FROM tblQuiz q LEFT JOIN tblUserQuizResult r ON q.quizSeq = r.quizSeq AND r.userSeq = ? \n"
           		+ "WHERE q.subjectSeq = s.subjectSeq AND r.userQuizResultSeq IS NOT NULL) as attemptedQuestions FROM tblSubject s WHERE s.techSeq = ?";
            
            try (PreparedStatement pstat = conn.prepareStatement(sql)) {
                pstat.setInt(1, userSeq);
                pstat.setInt(2, userSeq);
                pstat.setInt(3, techSeq);
                
                try (ResultSet rs = pstat.executeQuery()) {
                    while (rs.next()) {
                        SubjectQuizDTO dto = new SubjectQuizDTO();
                        dto.setSubjectSeq(rs.getInt("subjectSeq"));
                        dto.setSubjectName(rs.getString("subjectName"));
                        int totalQuestions = rs.getInt("totalQuestions");
                        int correctAnswers = rs.getInt("correctAnswers");
                        int attemptedQuestions = rs.getInt("attemptedQuestions");
                        
                        // 진행률 계산 (시도한 문제 수 / 전체 문제 수)
                        if (totalQuestions > 0) {
                            dto.setProgressPercentage((attemptedQuestions * 100) / totalQuestions);
                        } else {
                            dto.setProgressPercentage(0);
                        }
                        
                        // 정답률 계산 (맞은 문제 수 / 전체 문제 수) - 최대 100%
                        if (totalQuestions > 0 && attemptedQuestions > 0) {
                            int rate = (correctAnswers * 100) / totalQuestions;
                            // 정답률은 최대 100%로 제한
                            dto.setCorrectRate(Math.min(rate, 100));
                        } else {
                            dto.setCorrectRate(0);
                        }
                        
                        setColors(dto);
                        list.add(dto);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return list;
        }

    /**
     * progress bar의 상태를 표시하는 메서드입니다.
     * @param dto SubjectQuizDTO
     */
    private void setColors(SubjectQuizDTO dto) {
        int correctRate = dto.getCorrectRate();
        if (correctRate >= 80) {
            dto.setProgressBarColor("bg-success");
            dto.setBadgeColor("text-bg-success");
        } else if (correctRate >= 60) {
            dto.setProgressBarColor("bg-primary");
            dto.setBadgeColor("text-bg-primary");
        } else if (correctRate >= 40) {
            dto.setProgressBarColor("bg-warning");
            dto.setBadgeColor("text-bg-warning");
        } else {
            dto.setProgressBarColor("bg-danger");
            dto.setBadgeColor("text-bg-danger");
        }
    }
}
