package com.neo.bookhouse.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@TableName("code")
public class Code extends Model<Code>{
    
	//BigDecimal
	@TableId
	private Long codeId;
	
	private Long bookId;

	private Integer codeType;
	
	private String codeContent;
	
	private LocalDateTime codeStart;
	
    private LocalDateTime codeEnd;
	
	private Integer codeState;
	
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

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public String getCodeContent() {
		return codeContent;
	}

	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}

	public LocalDateTime getCodeStart() {
		return codeStart;
	}

	public void setCodeStart(LocalDateTime codeStart) {
		this.codeStart = codeStart;
	}

	public LocalDateTime getCodeEnd() {
		return codeEnd;
	}

	public void setCodeEnd(LocalDateTime codeEnd) {
		this.codeEnd = codeEnd;
	}

	public Integer getCodeState() {
		return codeState;
	}

	public void setCodeState(Integer codeState) {
		this.codeState = codeState;
	}
	

	@Override
	public String toString() {
		return "Code [codeId=" + codeId + ", bookId=" + bookId + ", codeType=" + codeType + ", codeContent="
				+ codeContent + ", codeStart=" + codeStart + ", codeEnd=" + codeEnd + ", codeState=" + codeState + "]";
	}

	
}
