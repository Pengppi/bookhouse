package com.neo.bookhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-08-25 23:55:01
 */
@SuppressWarnings("serial")
@Data
@TableName("user")
public class User extends Model<User> {

	@TableId(type = IdType.AUTO)//主键自增
    private String userId;

    private String userPassword;

    private String userName;

    private String userPhoto;

    private String userPhone;

    private String userBookhouse;

    private String userAddress;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserBookhouse() {
        return userBookhouse;
    }

    public void setUserBookhouse(String userBookhouse) {
        this.userBookhouse = userBookhouse;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.userId;
    }
}

