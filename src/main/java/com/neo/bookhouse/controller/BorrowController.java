package com.neo.bookhouse.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Borrow;
import com.neo.bookhouse.service.BookService;
import com.neo.bookhouse.service.BorrowService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/borrow")
@Slf4j
public class BorrowController {
	
	
}
