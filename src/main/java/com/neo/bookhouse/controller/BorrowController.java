package com.neo.bookhouse.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.dto.BookDto;
import com.neo.bookhouse.dto.BorrowDto;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Borrow;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BorrowService;
import com.neo.bookhouse.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/borrow")
@Slf4j
public class BorrowController {
	
	@Autowired
	BorrowService borrowService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/listById/{userId}/{mode}/{page}/{pageSize}")//根据用户ID查询借书记录，参数为用户ID，查询模式(0表示全部查，1表示查询未归还的，2表示查询已经归还的)，页号和页面大小
	public R<Page> getBorrowDtoById(@PathVariable Long userId, @PathVariable int mode, @PathVariable int page, @PathVariable int pageSize)
	{	
		  //分页构造器
        Page<Borrow> pageBuilder = new Page<>(page, pageSize);
        LambdaQueryWrapper<Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Borrow::getUserId, userId);
        if(mode == 1)//查询未归还的
        	queryWrapper.isNull(Borrow::getReturnDate);
        else if(mode == 2)//查询已经归还的
        	queryWrapper.isNotNull(Borrow::getReturnDate);
        
        borrowService.page(pageBuilder, queryWrapper);

        Page<BorrowDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(pageBuilder, dtoPage, "records");

        List<BorrowDto> borrowDtos = pageBuilder.getRecords().stream().map(borrow -> {
            BorrowDto borrowDto = new BorrowDto();
            BeanUtils.copyProperties(borrow, borrowDto);
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Book::getBookId, borrow.getBookId());
            Book book = bookService.getOne(wrapper);
            if(book != null)
            {
            borrowDto.setBookAuthor(book.getBookAuthor());
            //borrowDto.setBookBorrow(book.getBookBorrow());//根据现在书的情况
            borrowDto.setBookName(book.getBookName());
            borrowDto.setBorrowDate(borrow.getBorrowDate());
            borrowDto.setReturnDate(borrow.getReturnDate());
            borrowDto.setBookBorrow(borrowDto.getReturnDate() == null ? 1 : 0);//根据借书记录是否有返回日期（当时的情况）
            
            Long book_user_id = book.getUserId();
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, book_user_id));
            if(user != null)
            	borrowDto.setUserBookhouse(user.getUserBookhouse());//设置书屋名字
            return borrowDto;
            }
            return null;
        }).collect(Collectors.toList());

        dtoPage.setRecords(borrowDtos);
        return R.success(dtoPage);
	}
	
	@GetMapping("/findById/{userId}")//根据用户ID查询借书记录，参数为用户ID
	public R<List<BorrowDto>> getBorrowDtoById2(@PathVariable Long userId)
	{	
        LambdaQueryWrapper<Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Borrow::getUserId, userId);
        List<Borrow>borrows = borrowService.list(queryWrapper);

        List<BorrowDto> borrowDtos = borrows.stream().map(borrow -> {
            BorrowDto borrowDto = new BorrowDto();
            BeanUtils.copyProperties(borrow, borrowDto);
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Book::getBookId, borrow.getBookId());
            Book book = bookService.getOne(wrapper);
            if(book != null)
            {
            borrowDto.setBookAuthor(book.getBookAuthor());
            //borrowDto.setBookBorrow(book.getBookBorrow());//根据现在书的情况
            borrowDto.setBookName(book.getBookName());
            borrowDto.setBorrowDate(borrow.getBorrowDate());
            borrowDto.setReturnDate(borrow.getReturnDate());
            borrowDto.setBookBorrow(borrowDto.getReturnDate() == null ? 1 : 0);//根据借书记录是否有返回日期（当时的情况）
            
            Long book_user_id = book.getUserId();
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, book_user_id));
            if(user != null)
            	borrowDto.setUserBookhouse(user.getUserBookhouse());//设置书屋名字
            return borrowDto;
            }
            return null;
        }).collect(Collectors.toList());

        return R.success(borrowDtos);
	}
	
}
