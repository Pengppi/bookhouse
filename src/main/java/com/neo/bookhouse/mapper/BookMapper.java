/**
 * @Author: Neo
 * @Date: 2022/09/01 星期四 14:15:09
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.bookhouse.dto.BookDto;
import com.neo.bookhouse.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

    List<BookDto> getDtoList(Integer bookKind);//按标签查找
    
    List<BookDto> getDtoList2(String bookName);//按书名查找

}
