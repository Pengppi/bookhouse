package com.neo.bookhouse.entity;

import java.sql.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

@SuppressWarnings("serial")
public class Code extends Model<Code>{
   
	private Long codeId;
	
	private Long bookId;
	
	@Override
	public String toString() {
		return "Code [codeId=" + codeId + ", bookId=" + bookId + ", codeType=" + codeType + ", codeContent="
				+ codeContent + ", codeStart=" + codeStart + ", codeEnd=" + codeEnd + ", codeState=" + codeState + "]";
	}

	private Short codeType;
	
	private String codeContent;
	
	private Date codeStart;
	
	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Short getCodeType() {
		return codeType;
	}

	public void setCodeType(Short codeType) {
		this.codeType = codeType;
	}

	public String getCodeContent() {
		return codeContent;
	}

	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}

	public Date getCodeStart() {
		return codeStart;
	}

	public void setCodeStart(Date codeStart) {
		this.codeStart = codeStart;
	}

	public Date getCodeEnd() {
		return codeEnd;
	}

	public void setCodeEnd(Date codeEnd) {
		this.codeEnd = codeEnd;
	}

	public Short getCodeState() {
		return codeState;
	}

	public void setCodeState(Short codeState) {
		this.codeState = codeState;
	}

	private Date codeEnd;
	
	private Short codeState;
}
