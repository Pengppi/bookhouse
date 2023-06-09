package com.neo.bookhouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.bookhouse.entity.Booktag;

import java.util.List;

public interface BookTagService extends IService<Booktag> {
    public List<String> getIsbnByTag(Integer tag); //根据标签查找书籍
}
