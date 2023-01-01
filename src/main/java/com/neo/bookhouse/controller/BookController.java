/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:16:54
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")//新增书籍
    public R<String> add(@RequestBody Book book, HttpServletRequest request) {
        log.info("添加书籍：{}", book);
        //String userId = request.getAttribute("userId").toString();
        //book.setUserId(userId);
        book.setUserId(1L);
        boolean flg = bookService.save(book);
        if (flg) {
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    @DeleteMapping("/delete/{id}")//删除书籍
    public R<String> delete(@PathVariable Long id)//路径变量
    {
        bookService.removeById(id);
        return R.success("删除成功");
    }
    
    @PutMapping("/update")//编辑书籍
     public R<String>update(@RequestBody Book book)
     {
    	bookService.updateById(book);
    	return R.success("修改成功");
     }
    
    @GetMapping("/findAll/{id}")//查询书屋的信息
    public R<List<Book>> getBookByUserId(@PathVariable Long id)//路径变量
    {
    	LambdaQueryWrapper<Book>queryWrapper = new LambdaQueryWrapper<>();
    	queryWrapper.eq(Book::getUserId, id);
    	return R.success(bookService.list(queryWrapper));
    }

}
