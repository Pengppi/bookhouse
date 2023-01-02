/**
 * @Author: Neo
 * @Date: 2023/01/02 星期一 16:53:50
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.bookhouse.entity.Contact;

public interface ContactService extends IService<Contact> {

    boolean updateUnreadCount(Long senderId, Long receiverId);

    boolean updateUnreadCountToZero(Long senderId, Long receiverId);
}
