package com.neo.bookhouse;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.neo.bookhouse.entity.Contact;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.service.ContactService;
import com.neo.bookhouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BookHouseApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @Test
    void contextLoads() {
        LambdaUpdateWrapper<Contact> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Contact::getSenderId, 1L);
        updateWrapper.eq(Contact::getReceiverId, 222L);
        updateWrapper.set(Contact::getUnreadCount, 20);
        contactService.update(updateWrapper);


    }

}
