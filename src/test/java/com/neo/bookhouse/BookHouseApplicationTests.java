package com.neo.bookhouse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.mapper.BookMapper;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BookTagService;
import com.neo.bookhouse.service.ContactService;
import com.neo.bookhouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

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
    void contextLoads() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, 48);
        queryWrapper.select(User::getUserLongitude, User::getUserLatitude);
        Map<String, Object> map = userService.getMap(queryWrapper);
        log.info("map: {}", map);
    }
}
