/**
 * @Author: Neo
 * @Date: 2023/01/02 星期一 16:59:06
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.dto.ContactDto;
import com.neo.bookhouse.entity.Chat;
import com.neo.bookhouse.entity.Contact;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.service.ChatService;
import com.neo.bookhouse.service.ContactService;
import com.neo.bookhouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public R<List<ContactDto>> list(@RequestParam("userId") Long userId) {
        LambdaQueryWrapper<Contact> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Contact::getReceiverId, userId);
        queryWrapper.orderByDesc(Contact::getUpdateTime);
        List<Contact> contacts = contactService.list(queryWrapper);
        List<ContactDto> contactDtos = contacts.stream().map(contact -> {
            ContactDto contactDto = new ContactDto();
            contactDto.setUserId(contact.getSenderId());
            contactDto.setUnreadCount(contact.getUnreadCount());
            contactDto.setUpdateTime(contact.getUpdateTime());
            User sender = userService.getById(contact.getSenderId());
            contactDto.setUserName(sender.getUserName());
            contactDto.setUserPhoto(sender.getUserPhoto());
            return contactDto;
        }).collect(Collectors.toList());
        return R.success(contactDtos);
    }

    @PostMapping("/send")
    public R<String> send(@RequestBody Chat chat) {
        if (chatService.save(chat)) {


            return R.success("发送成功");
        }
        return R.error("发送失败");
    }

}
