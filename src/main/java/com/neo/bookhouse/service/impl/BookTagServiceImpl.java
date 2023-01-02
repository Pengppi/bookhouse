package com.neo.bookhouse.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Booktag;
import com.neo.bookhouse.mapper.BookTagMapper;
import com.neo.bookhouse.service.BookTagService;


@Service
public class BookTagServiceImpl extends ServiceImpl<BookTagMapper, Booktag> implements BookTagService{

}
