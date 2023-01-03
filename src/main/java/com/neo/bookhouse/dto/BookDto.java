/**
 * @Author: Neo
 * @Date: 2023/01/03 星期二 21:53:17
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.dto;

import com.neo.bookhouse.entity.Book;
import lombok.Data;

@Data
public class BookDto {
    private Long bookId;

    private Long userId;

    private String bookName;

    private String bookAuthor;

    private Integer bookKind;

    private String bookPhoto;

    private String bookPublisher;

    private String bookVersion;

    private String bookIsbn;

    private Integer bookQuality;

    private Integer bookBorrow;

    private Double userLongitude;   //经度

    private Double userLatitude;   //纬度
}
