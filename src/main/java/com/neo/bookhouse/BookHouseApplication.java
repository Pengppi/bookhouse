package com.neo.bookhouse;

import com.neo.bookhouse.common.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class BookHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookHouseApplication.class, args);
        log.info("项目启动成功...");
        BaseContext.setCurrentId(1L);
    }

}
