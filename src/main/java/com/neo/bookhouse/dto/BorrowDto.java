package com.neo.bookhouse.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BorrowDto {
	
	private Long bookId;//书的ID，用于检验还书码
	
    private String bookName;//书名
    
    private String bookAuthor;//书的作者
    
    private String userBookhouse;//书主的书屋名字
    
    private LocalDateTime borrowDate;//借书日期
    
    private LocalDateTime returnDate;//还书日期
    
    private Integer bookBorrow;//书籍状态（1表示借入）
    
}
