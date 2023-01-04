/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:14:09
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.dto.BookDto;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.mapper.BookMapper;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<BookDto> getDtoList(Integer bookKind) //用于标签查找
    {
        return bookMapper.getDtoList(bookKind);
    }
   
    @Override
	public List<BookDto> getDtoList2(String bookName) {//用于书名查找
		return bookMapper.getDtoList2(bookName);
	}

    @Override
    public Map<String, Object> getLocation(Long bookId) {

        return null;
    }
    
    @Override
	public List<Long> getIdLikeName(String name)//根据字符串查找书籍的ID 
    {
		LambdaQueryWrapper<Book>queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(Book::getBookName, name);
		queryWrapper.select(Book::getBookId);
		return this.listObjs(queryWrapper, o -> Long.valueOf(o.toString()));
	}

	
}
