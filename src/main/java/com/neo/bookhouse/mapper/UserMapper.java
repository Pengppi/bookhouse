/**
 * @Author: Neo
 * @Date: 2022/08/25 星期四 23:55:38
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.bookhouse.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

}
