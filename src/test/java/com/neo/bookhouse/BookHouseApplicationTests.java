package com.neo.bookhouse;

import com.neo.bookhouse.dto.BookDto;
import com.neo.bookhouse.mapper.BookMapper;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BookTagService;
import com.neo.bookhouse.service.ContactService;
import com.neo.bookhouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class BookHouseApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Autowired
    BookService bookService;

    @Autowired
    BookTagService bookTagService; //标签的修改

    @Autowired
    BookMapper bookMapper;

    @Test
    void testPage() {
        List<BookDto> list = bookService.getDtoListByPage(1, 3, 5);
        log.info(list.size() + "");
    }

    @Test
    void count() {
        log.info("count: {}", bookMapper.countDtoList(1));
    }
}
