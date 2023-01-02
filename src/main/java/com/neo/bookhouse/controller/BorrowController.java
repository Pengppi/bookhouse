package com.neo.bookhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.bookhouse.service.BorrowService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/borrow")
@Slf4j
public class BorrowController {

	@Autowired
    BorrowService borrowService;
}
