package com.neo.bookhouse.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.bookhouse.entity.Booktag;

@Mapper
public interface BookTagMapper extends BaseMapper<Booktag>{

}
