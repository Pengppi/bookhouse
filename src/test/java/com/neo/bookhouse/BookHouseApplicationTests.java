package com.neo.bookhouse;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.entity.User;
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

    @Test
    void contextLoads() {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, "1");
        User one = userService.getOne(queryWrapper);
        log.info("json:{}", JSON.toJSONString(one));

    }

}
