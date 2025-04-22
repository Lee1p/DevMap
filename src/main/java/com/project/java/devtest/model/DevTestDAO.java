package com.project.java.devtest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DevTestDAO {
    private DataSource dataSource;

    public DevTestDAO() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/pool");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getOptionSeqByCode(String optionCode) {
        String query = "SELECT optionSeq FROM tblQuestionOption WHERE optionCode = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, optionCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("optionSeq");  // 옵션 코드에 해당하는 optionSeq 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // 옵션 코드가 없으면 -1 반환
    }

    

    public List<DevTestDTO> getAllQuestionsWithOptions() {
        Map<Integer, DevTestDTO> questionMap = new HashMap<>();
        String sql = "SELECT q.questionSeq, q.questionText, o.optionSeq, o.optionCode, o.optionText " +
                     "FROM tblQuestion q " +
                     "LEFT JOIN tblQuestionOption o ON q.questionSeq = o.questionSeq " +
                     "ORDER BY q.questionSeq, o.optionSeq";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int questionSeq = rs.getInt("questionSeq");
                
                // 새 질문을 만나면 질문 객체 생성
                if (!questionMap.containsKey(questionSeq)) {
                    DevTestDTO question = new DevTestDTO();
                    question.setQuestionSeq(questionSeq);
                    question.setQuestionText(rs.getString("questionText"));
                    question.setOptions(new ArrayList<>());
                    questionMap.put(questionSeq, question);
                }
                
                // 선택지 추가
                OptionDTO option = new OptionDTO();
                option.setOptionSeq(rs.getInt("optionSeq"));
                option.setQuestionSeq(questionSeq);
                option.setOptionCode(rs.getString("optionCode"));
                option.setOptionText(rs.getString("optionText"));
                
                questionMap.get(questionSeq).getOptions().add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Map을 List로 변환하여 반환
        return new ArrayList<>(questionMap.values());
    }
     
}