package com.neo.bookhouse.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Code;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.CodeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/code")
@Slf4j
public class CodeController {

	@Autowired
    CodeService codeService;
	
	@Autowired
	BookService bookService;
	
	//借书码的有效时长
	public static final long TEN_MINUTE = 10L;//十分钟
	
	 @PostMapping("/add")//新增借书还书码
	    public R<String> add(@RequestBody Code code) {
	        log.info("生成码：{}", code);
	        //判断书是否存在
	        Book book = bookService.getOne(new LambdaQueryWrapper<Book>().eq(Book::getBookId, code.getBookId()));
	        if(book == null)//书不存在
	        	return R.error("书不存在，不能生成借书码");
	        //判断书的状态，若被借走则不能生成借书码
	        Integer state = book.getBookBorrow();
	        if(state.equals(1) && code.getCodeType().equals(0))//表示书已经被借走了。
	        	return R.error("书已经被借走");
	        if(state.equals(0) && code.getCodeType().equals(1))//表示书还没有借出去，不能还书
	        	return R.error("书没有被借走");
	        
	        //借书码已经存在也不能生成借书码
	        Code code2 = codeService.getOne(new LambdaQueryWrapper<Code>().eq(Code::getBookId, code.getBookId()));
	        if(code2 != null)//已经存在借书码,要保证借书码的唯一性
	        	return R.error("借书码已经存在");
	        
	        LocalDateTime localDateTime = LocalDateTime.now();
	        code.setCodeStart(localDateTime);//借书码的生成时间
	        code.setCodeEnd(localDateTime.plusMinutes(TEN_MINUTE));//借书码的失效时间
	        code.setCodeState(1);//设成状态有效
	        
	        //时间比较isBefore
	        
	        boolean flg = codeService.save(code);
	        if (flg) {
	            return R.success(code.getCodeType() == 0? "借书码生成成功" : "还书码生成成功");
	        }
	        return R.error(code.getCodeType() == 0? "借书码生成失败" : "还书码生成失败");
	    }
	 
	 
	 @PostMapping("/check/{bookId}/{codeStr}")//检验借书还书码
	    public R<String> check(@PathVariable Long bookId,@PathVariable String codeStr)//书的ID, 要检验的字符串
	    {
		   LocalDateTime dateTime = LocalDateTime.now();
		   LambdaQueryWrapper<Code>wrapper = new LambdaQueryWrapper<>();
		   wrapper.eq(Code::getBookId, bookId);
		   Code code = codeService.getOne(wrapper);
		   
		   if(code == null)return R.error("借书码不存在");
		   
		   //检验是否超时
		   if(dateTime.isAfter(code.getCodeEnd()))//已经超时
		   {
			   codeService.removeById(code);//删除借书码,借书码无效
			   return R.success("匹配超时，借书码已经失效");
		   }
		   //检验是否一致
		   boolean flg = code.getCodeContent().equals(codeStr);
		   
		   if(flg == true)//检验成功
		   {
			   codeService.removeById(code);//删除借书码
			   return R.success("匹配成功");
		   }
		   else //检验失败
		   return R.error("匹配失败");
	    }
	 
	   @DeleteMapping("/delete/{bookId}")//删除对应bookId的借书码（借书码失效等）
	      public R<String> delete(@PathVariable Long bookId)
	      {
		    LambdaQueryWrapper<Code>wrapper = new LambdaQueryWrapper<>();
		    wrapper.eq(Code::getBookId,bookId);
		    boolean flg = codeService.remove(wrapper);
		    return flg ? R.success("删除成功") : R.error("删除失败");
	      }
}