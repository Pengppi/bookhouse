package com.neo.bookhouse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * (Chat)实体类
 *
 * @author makejava
 * @since 2023-01-01 18:48:14
 */
@Data
public class Chat {

    private Long senderId;

    private Long receiverId;

    private LocalDateTime chatTime;

    private Integer chatType;

    private String chatContent;

}

