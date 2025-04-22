package com.project.java.devtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OptionDTO {
    private int optionSeq;
    private int questionSeq;
    private String optionCode;
    private String optionText;
	public synchronized int getOptionSeq() {
		return optionSeq;
	}
	public synchronized void setOptionSeq(int optionSeq) {
		this.optionSeq = optionSeq;
	}
	public synchronized int getQuestionSeq() {
		return questionSeq;
	}
	public synchronized void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}
	public synchronized String getOptionCode() {
		return optionCode;
	}
	public synchronized void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}
	public synchronized String getOptionText() {
		return optionText;
	}
	public synchronized void setOptionText(String optionText) {
		this.optionText = optionText;
	}
    
    
}