package com.project.java.devtest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DevFieldDTO {
    private int fieldSeq;
    private String fieldName;
	public synchronized int getFieldSeq() {
		return fieldSeq;
	}
	public synchronized void setFieldSeq(int fieldSeq) {
		this.fieldSeq = fieldSeq;
	}
	public synchronized String getFieldName() {
		return fieldName;
	}
	public synchronized void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
    
    
}
