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

import java.time.LocalDateTime;
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

    @PostMapping("/list/{userId}")
    public R<List<ContactDto>> list(@PathVariable Long userId) {
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
        chat.setChatTime(LocalDateTime.now());
        if (chatService.save(chat)) {
            contactService.updateUnreadCount(chat.getSenderId(), chat.getReceiverId());
            return R.success("发送成功");
        }
        return R.error("发送失败");
    }

    @PostMapping("/{userId}/{anotherId}")
    public R<String> build(@PathVariable Long userId, @PathVariable Long anotherId) {
        Contact contact = new Contact();
        contact.setSenderId(anotherId);
        contact.setReceiverId(userId);
        contact.setUnreadCount(0);
        if (contactService.save(contact)) {
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    @GetMapping("/read/{userId}/{anotherId}")
    public R<String> read(@PathVariable Long userId, @PathVariable Long anotherId) {
        if (contactService.updateUnreadCountToZero(anotherId, userId)) {
            return R.success("更新成功");
        }
        return R.error("更新失败");
    }

    @DeleteMapping("/{userId}/{anotherId}")
    public R<String> delete(@PathVariable Long userId, @PathVariable Long anotherId) {
        LambdaQueryWrapper<Contact> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Contact::getSenderId, anotherId);
        queryWrapper.eq(Contact::getReceiverId, userId);
        if (contactService.remove(queryWrapper)) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
