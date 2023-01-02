package com.neo.bookhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Book)表实体类
 *
 * @author makejava
 * @since 2022-09-01 14:11:51
 */
@Data
public class Book {

    @TableId(type = IdType.AUTO)
    private Long bookId;

    private Long userId;

    private String bookName;

    private String bookAuthor;

    private String bookKind;

    private String bookPhoto;

    private String bookPublisher;

    private String bookVersion;

    private String bookIsbn;

    private Integer bookQuality;

    private Integer bookBorrow;
    

}

