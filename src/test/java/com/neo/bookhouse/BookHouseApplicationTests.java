package com.neo.bookhouse;

import com.neo.bookhouse.dto.BookDto;
import com.neo.bookhouse.mapper.BookMapper;
import com.neo.bookhouse.service.*;
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

    @Autowired
    SendMailService sendMailService;

    @Test
    void test() {

    }


}
