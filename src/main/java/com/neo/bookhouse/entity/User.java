package com.neo.bookhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-08-25 23:55:01
 */
@Data
public class User {

    @TableId(type = IdType.AUTO)//主键自增
    private Long userId;

    private String userName;

    private String userPassword;

    private String userMail;

    private String userPhone;

    private String userPhoto;

    private String userBookhouse;

    private String userAddress;

    private Integer userKind;
}

