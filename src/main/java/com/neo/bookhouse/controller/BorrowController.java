package com.neo.bookhouse.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Borrow;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BorrowService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/borrow")
@Slf4j
public class BorrowController {

	@Autowired
    BorrowService borrowService;
	
	@Autowired
	BookService bookService;
	
	public static final long ONE_MONTH = 1L;//一个月的时间
	public static final long FIFTEEN_DAY = 15L;//15天时间
	
	 @PostMapping("/add")//新增借书记录
	    public R<String> add(@RequestBody Borrow borrow) {
	        log.info("生成借书记录：{}", borrow);
	        
	        Book book = bookService.getOne(new LambdaQueryWrapper<Book>().eq(Book::getBookId, borrow.getBookId()));
	        if(book == null)
	        return R.error("该书不存在，借书失败");
	        
	        Integer bookState = book.getBookBorrow();
	        if(bookState == 1)//书正在被借中,保证了只有一条借书记录是正在借书中
	        return R.error("该书被借走，借书失败");
	        
	        LocalDateTime localDateTime = LocalDateTime.now();
	        borrow.setBorrowDate(localDateTime);//借书的时间
	        borrow.setShouldDate(localDateTime.plusDays(FIFTEEN_DAY));//借书码的失效时间
	        borrow.setBorrowState(0);//借书记录状态设为0，表示借书中。
	        
	        //时间比较isBefore
	        
	        boolean flg = borrowService.save(borrow);
	        if (flg) {
	        	//更改借书状态
	        	book.setBookBorrow(1);//将书的状态设为1表示被借走。
	        	bookService.updateById(book);
	            return R.success("借书成功");//返回借书的时间
	        }
	        return R.error("借书失败");
	    }
	 
	 @PutMapping("/update/{state}")//编辑借书记录（在还书时改变）,state表示要更改的借书记录原本的状态，只能是0在借中,2未归还
     public R<String>update(@RequestBody Borrow borrow, @PathVariable Integer state)
     {
		 if(state == 1)//不能更改已经归还的借书记录
		  return R.success("不能更改借书记录状态已经归还的记录");
    	LambdaQueryWrapper<Borrow>wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(Borrow::getBookId, borrow.getBookId());
        wrapper.eq(Borrow::getBorrowState, state);
        wrapper.eq(Borrow::getUserId, borrow.getUserId());
        
    	boolean flg = borrowService.update(borrow, wrapper);
    	if(flg)
    	{
    		if(borrow.getBorrowState() == 1)//表示已经归还
    		{
    			//更改书的状态
    			Book book = bookService.getOne(new LambdaQueryWrapper<Book>().eq(Book::getBookId,borrow.getBookId()));
    			book.setBookBorrow(0);//将书的状态设为0表示书已经归还。
	        	bookService.updateById(book);
    		}
    		R.success("修改借书记录成功");
    	}
    	return R.success("修改借书记录失败");
     }
}
