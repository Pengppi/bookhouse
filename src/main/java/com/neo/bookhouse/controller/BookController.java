/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:16:54
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.controller;


import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public R<String> add(@RequestBody Book book, HttpServletRequest request) {
        log.info("添加书籍：{}", book);
        //String userId = request.getAttribute("userId").toString();
        //book.setUserId(userId);
        book.setUserId("1");
        boolean flg = bookService.save(book);
        //if (flg) {
        //    return R.success("添加成功");
        //}
        return R.error("添加失败");
    }

    public R<String> delete()
    {

        return null;
    }


}
