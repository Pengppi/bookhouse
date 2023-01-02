package com.neo.bookhouse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.neo.bookhouse.common.BaseContext;

@SpringBootApplication
@Slf4j
public class BookHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookHouseApplication.class, args);
        log.info("项目启动成功...");
        BaseContext.setCurrentId(1L);
    }

}
