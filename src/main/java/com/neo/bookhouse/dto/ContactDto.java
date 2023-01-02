/**
 * @Author: Neo
 * @Date: 2023/01/02 星期一 18:13:27
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactDto {

    private Long userId;

    private String userName;

    private String userPhoto;

    private Integer unreadCount;

    private LocalDateTime updateTime;

}
