/**
 * @Author: Neo
 * @Date: 2022/08/25 星期四 23:57:08
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.mapper.UserMapper;
import com.neo.bookhouse.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
