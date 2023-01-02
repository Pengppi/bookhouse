/**
 * @Author: Neo
 * @Date: 2023/01/02 星期一 16:54:15
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Contact;
import com.neo.bookhouse.mapper.ContactMapper;
import com.neo.bookhouse.service.ContactService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {
    @Override
    public boolean updateUnreadCount(Long senderId, Long receiverId) {
        LambdaQueryWrapper<Contact> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Contact::getSenderId, senderId);
        queryWrapper.eq(Contact::getReceiverId, receiverId);
        Contact contact = getOne(queryWrapper);
        if (contact != null) {
            LambdaUpdateWrapper<Contact> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Contact::getSenderId, senderId);
            updateWrapper.eq(Contact::getReceiverId, receiverId);
            updateWrapper.set(Contact::getUnreadCount, contact.getUnreadCount() + 1);
            updateWrapper.set(Contact::getUpdateTime, LocalDateTime.now());
            return update(updateWrapper);
        } else {
            contact = new Contact();
            contact.setSenderId(senderId);
            contact.setReceiverId(receiverId);
            contact.setUnreadCount(1);
            return save(contact);
        }
    }

    @Override
    public boolean updateUnreadCountToZero(Long senderId, Long receiverId) {
        LambdaQueryWrapper<Contact> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Contact::getSenderId, senderId);
        queryWrapper.eq(Contact::getReceiverId, receiverId);
        Contact contact = getOne(queryWrapper);
        if (contact != null) {
            LambdaUpdateWrapper<Contact> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Contact::getSenderId, senderId);
            updateWrapper.eq(Contact::getReceiverId, receiverId);
            updateWrapper.set(Contact::getUnreadCount, 0);
            updateWrapper.set(Contact::getUpdateTime, LocalDateTime.now());
            return update(updateWrapper);
        } else {
            contact = new Contact();
            contact.setSenderId(senderId);
            contact.setReceiverId(receiverId);
            contact.setUnreadCount(0);
            return save(contact);
        }
    }
}
