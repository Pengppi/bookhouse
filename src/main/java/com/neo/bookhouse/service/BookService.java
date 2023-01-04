/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:12:41
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.bookhouse.dto.BookDto;
import com.neo.bookhouse.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService extends IService<Book> {

    List<BookDto> getDtoListByPage(Integer bookKind, int page, int pageSize);//用于标签查找

    Map<String, Object> getLocation(Long bookId);

    public List<BookDto> getDtoList2(String bookName);

    public List<Long> getIdLikeName(String name); //根据字符串查找书籍

    int countDtoList(Integer bookKind);
}
