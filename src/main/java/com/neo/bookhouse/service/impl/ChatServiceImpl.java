/**
 * @Author: Neo
 * @Date: 2023/01/01 星期日 18:55:31
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Chat;
import com.neo.bookhouse.mapper.ChatMapper;
import com.neo.bookhouse.service.ChatService;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {


}
