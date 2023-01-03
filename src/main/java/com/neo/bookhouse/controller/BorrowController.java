package com.neo.bookhouse.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.Borrow;
import com.neo.bookhouse.service.BorrowService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/borrow")
@Slf4j
public class BorrowController {

	@Autowired
    BorrowService borrowService;
	
	public static final long ONE_MONTH = 1L;//一个月的时间
	public static final long FIFTEEN_DAY = 15L;//15天时间
	
	 @PostMapping("/add")//新增借书记录
	    public R<String> add(@RequestBody Borrow borrow) {
	        log.info("生成借书记录：{}", borrow);
	        
	        LocalDateTime localDateTime = LocalDateTime.now();
	        borrow.setBorrowDate(localDateTime);//借书的时间
	        borrow.setShouldDate(localDateTime.plusDays(FIFTEEN_DAY));//借书码的失效时间
	        borrow.setBorrowState(0);//借书记录状态设为0，表示借书中。
	        
	        //时间比较isBefore
	        
	        boolean flg = borrowService.save(borrow);
	        if (flg) {
	            return R.success(localDateTime.toString());//返回借书的时间
	        }
	        return R.error("借书记录生成失败");
	    }
	 
	 @PutMapping("/update")//编辑借书记录（在还书时改变）
     public R<String>update(@RequestBody Borrow borrow)
     {
    	LambdaQueryWrapper<Borrow>wrapper = new LambdaQueryWrapper<>();
    	wrapper.eq(Borrow::getBookId, borrow.getBookId());
        wrapper.eq(Borrow::getBorrowDate, borrow.getReturnDate());
        wrapper.eq(Borrow::getUserId, borrow.getUserId());
        
    	boolean flg = borrowService.update(borrow, wrapper);
    	if(flg)
    	{
    		R.success("修改借书记录成功");
    	}
    	return R.success("修改借书记录失败");
     }
}
