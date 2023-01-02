/**
 * @Author: Neo
 * @Date: 2023/01/02 星期一 16:54:15
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Contact;
import com.neo.bookhouse.mapper.ContactMapper;
import com.neo.bookhouse.service.ContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {


}
