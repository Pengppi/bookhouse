/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:14:09
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.mapper.BookMapper;
import com.neo.bookhouse.service.BookService;
import org.springframework.stereotype.Service;

@Service("bookService")
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {


}
