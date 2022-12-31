package com.neo.bookhouse.entity;

import java.sql.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

@SuppressWarnings("serial")
public class Code extends Model<Code>{
   
	private Long codeId;
	
	private Long bookId;
	
	private Short codeType;
	
	private String codeContent;
	
	private Date codeStart;
	
	private Date codeEnd;
	
	private Short codeState;
}
