/**
 * @Author: Neo
 * @Date: 2023/01/01 星期日 18:54:45
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Borrow;
import com.neo.bookhouse.mapper.BorrowMapper;
import com.neo.bookhouse.service.BorrowService;
import org.springframework.stereotype.Service;

@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {


}
