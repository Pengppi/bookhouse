package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Book;
import com.neo.bookhouse.entity.Booktag;
import com.neo.bookhouse.mapper.BookTagMapper;
import com.neo.bookhouse.service.BookTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;


@Service
public class BookTagServiceImpl extends ServiceImpl<BookTagMapper, Booktag> implements BookTagService {

    @Override
    public List<String> getIsbnByTag(Integer tag) {
        LambdaQueryWrapper<Booktag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Booktag::getBookKind, tag);
        queryWrapper.select(Booktag::getBookIsbn);
        return this.listObjs(queryWrapper, obj -> obj.toString());
    }

}
