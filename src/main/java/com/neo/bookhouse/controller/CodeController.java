package com.neo.bookhouse.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Borrow;
import com.neo.bookhouse.entity.Code;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BorrowService;
import com.neo.bookhouse.service.CodeService;
import com.neo.bookhouse.utils.ValidateCodeUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/code")
@Slf4j
public class CodeController {

	@Autowired
    CodeService codeService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	BorrowService borrowService;
	
	//借书码的有效时长
	public static final long TEN_MINUTE = 10L;//十分钟
	
	//借书码的集合
	public static final Set<String>code_set = new HashSet<>();
	
	 @PostMapping("/add/{bookId}/{codeType}")//新增借书还书码
	    public R<String> add(@PathVariable Long bookId, @PathVariable Integer codeType) {
	
	        //判断书是否存在
	        Book book = bookService.getOne(new LambdaQueryWrapper<Book>().eq(Book::getBookId, bookId));
	        if(book == null)//书不存在
	        	return R.error("书不存在，不能生成借书码");
	        
	        //判断书的状态，排除不能生成借书码的情况
	        Integer state = book.getBookBorrow();
	        if(state.equals(1) && codeType.equals(0))//表示书已经被借走了。
	        	return R.error("书已经被借走，不能生成借书码");
	        if(state.equals(0) && codeType.equals(1))//表示书还没有借出去，不能还书
	        	return R.error("书没有被借走，不能生成还书码");
	        
	        //借书码已经存在也不能生成借书码
	        Code code = codeService.getOne(new LambdaQueryWrapper<Code>().eq(Code::getBookId, bookId));
	        if(code != null)//已经存在借书码,要保证借书码的唯一性
	        	return R.error("借书码已经存在");
	        
	        Code code2 = new Code();
	        String codeStr = null;
	        do{
	        	codeStr = ValidateCodeUtils.generateValidateCode(6).toString();
	        }while(!code_set.add(codeStr));
	        
	        code2.setCodeId(codeStr);//设置借书码的内容为codeStr
	        code2.setCodeType(codeType);//设置借书码的类型
	        code2.setBookId(bookId);//设置借书码对应的书籍ID
	        LocalDateTime localDateTime = LocalDateTime.now();
	        code2.setCodeStart(localDateTime);//借书码的生成时间
	        code2.setCodeEnd(localDateTime.plusMinutes(TEN_MINUTE));//借书码的失效时间
	        code2.setCodeState(1);//设成状态有效
	        
	        //生成借书记录
	        log.info("生成借书码：{}", code2);
	        boolean flg = codeService.save(code2);
	        if (flg) {
	            return R.success(codeStr);//返回借书码
	        }
	        return R.error(code2.getCodeType() == 0? "借书码生成失败" : "还书码生成失败");
	    }
	 
	 
	 @PostMapping("/check/{userId}/{codeStr}/{checkStr}")//检验借书还书码
	    public R<String> check(@PathVariable Long userId, @PathVariable String codeStr, @PathVariable String checkStr)//用户ID, 借书码内容, 要检验的字符串
	    {
		   LocalDateTime dateTime = LocalDateTime.now();
		   LambdaQueryWrapper<Code>wrapper = new LambdaQueryWrapper<>();
		   wrapper.eq(Code::getCodeId, codeStr);
		   Code code = codeService.getOne(wrapper);
		   
		   if(code == null)return R.error("借书码不存在");
		   
		   //检验是否超时
		   if(dateTime.isAfter(code.getCodeEnd()))//已经超时
		   {
			   codeService.remove(wrapper);
			   code_set.remove(codeStr);
			   return R.error("匹配超时，借书码已经失效");
		   }
		   //检验是否一致
		   boolean flg = codeStr.equals(checkStr);
		   
		   String borrow_state_str = "";//借书情况信息
		   if(flg == true)//检验成功
		   {
			   codeService.remove(wrapper);//删除借书码
			   code_set.remove(codeStr);
			   if(code.getCodeType().equals(0))//表示借书成功，生成借书记录
			   {
				   borrow_state_str = addBorrow(userId, code.getBookId());//借书是否成功返回信息
			   }
			   else //表示还书成功，更改借书记录
			   {
				   borrow_state_str = returnBorrow(userId, code.getBookId());//还书是否成功返回信息
			   }
			   return R.success("匹配成功,"+borrow_state_str);
		   }
		   else //检验失败
		   return R.error("匹配失败");
	    }
	 
	 public static final long ONE_MONTH = 1L;//一个月的时间
		public static final long FIFTEEN_DAY = 15L;//15天时间
		
		      //新增借书记录
		    public String addBorrow(Long userId, Long bookId) {
		        //log.info("生成借书记录：{}", borrow);
		        
		        Book book = bookService.getOne(new LambdaQueryWrapper<Book>().eq(Book::getBookId, bookId));
		        if(book == null)
		        return "该书不存在，借书失败";
		        
		        Integer bookState = book.getBookBorrow();
		        if(bookState == 1)//书正在被借中,保证了只有一条借书记录是正在借书中
		        return "该书被借走，借书失败";
		        
		        Borrow borrow = new Borrow();
		        LocalDateTime localDateTime = LocalDateTime.now();
		        
		        borrow.setBookId(bookId);//设置借书记录的书籍ID
		        borrow.setUserId(userId);//设置借书者的ID
		        borrow.setBorrowDate(localDateTime);//借书的时间
		        borrow.setShouldDate(localDateTime.plusDays(FIFTEEN_DAY));//借书码的失效时间
		        borrow.setBorrowState(0);//借书记录状态设为0，表示借书中。
		        
		        //时间比较isBefore
		        
		        boolean flg = borrowService.save(borrow);
		        if (flg) {
		        	//更改借书状态
		        	book.setBookBorrow(1);//将书的状态设为1表示被借走。
		        	bookService.updateById(book);
		            return "借书成功";//返回借书的时间
		        }
		        return "借书失败";
		    }
		 
		 //编辑借书记录（在还书时改变）
	     public String returnBorrow(Long userId, Long bookId)
	     {
	    	LambdaQueryWrapper<Borrow>wrapper = new LambdaQueryWrapper<>();
	    	wrapper.eq(Borrow::getBookId, bookId);
	        wrapper.eq(Borrow::getBorrowState, 0);
	        wrapper.eq(Borrow::getUserId, userId);
	        Borrow borrow_zero = borrowService.getOne(wrapper);//借书中的借书记录
	        
	        LambdaQueryWrapper<Borrow>wrapper2 = new LambdaQueryWrapper<>();
	        wrapper2.eq(Borrow::getBookId, bookId);
	        wrapper2.eq(Borrow::getBorrowState, 2);
	        wrapper2.eq(Borrow::getUserId, userId);
	        Borrow borrow_two = borrowService.getOne(wrapper2);//未归还的借书记录
	        
	        if(borrow_zero == null && borrow_two == null)//没有可以修改的借书记录
	        	return "没有相应的借书记录，还书失败";
	        
	        Borrow borrow = new Borrow();
	        LambdaQueryWrapper<Borrow>wrapper3 = new LambdaQueryWrapper<>();
	        wrapper3.eq(Borrow::getBookId, bookId);
	        wrapper3.eq(Borrow::getUserId, userId);
	        wrapper3.eq(Borrow::getBorrowState, borrow_zero == null ? 2:0);//保证选中借书记录为在借中0或者未归还2
	        borrow.setReturnDate(LocalDateTime.now());//自动生成归还日期。
	        borrow.setBorrowState(1);//把状态设成已归还
	        
	    	boolean flg = borrowService.update(borrow, wrapper3);
	    	if(flg == true)
	    	{
	    			//更改书的状态
	    			Book book = bookService.getOne(new LambdaQueryWrapper<Book>().eq(Book::getBookId,bookId));
	    			System.out.println("book: "+book);
	    			if(book != null)
	    			{
	    			book.setBookBorrow(0);//将书的状态设为0表示书已经归还。
		        	bookService.updateById(book);
	    			}
		        	
	    		return "还书成功";
	    	}
	    	return "还书失败";
	     }
	 
}