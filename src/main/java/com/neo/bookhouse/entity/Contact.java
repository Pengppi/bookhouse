/**
 * @Author: Neo
 * @Date: 2023/01/01 星期日 23:52:24
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Contact {

    private Long senderId;

    private Long receiverId;

    private Integer unreadCount;

    private LocalDateTime updateTime;
}
