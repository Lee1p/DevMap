package com.project.java.devtest.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DevTestDTO {
    private int questionSeq;
    private String questionText;
    private List<OptionDTO> options; // 질문의 선택지 목록 추가
	public synchronized int getQuestionSeq() {
		return questionSeq;
	}
	public synchronized void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}
	public synchronized String getQuestionText() {
		return questionText;
	}
	public synchronized void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public synchronized List<OptionDTO> getOptions() {
		return options;
	}
	public synchronized void setOptions(List<OptionDTO> options) {
		this.options = options;
	}
    
    
}

