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

    List<BookDto> getDtoList(Integer bookKind);

    Map<String, Object> getLocation(Long bookId);
}
