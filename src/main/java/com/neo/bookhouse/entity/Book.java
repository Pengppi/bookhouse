package com.neo.bookhouse.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (Book)表实体类
 *
 * @author makejava
 * @since 2022-09-01 14:11:51
 */
@SuppressWarnings("serial")
public class Book extends Model<Book> {
    
    private Long bookId;
    
    private String userId;
    
    private String bookName;
    
    private String bookPhoto;
    
    private String bookAuthor;
    
    private String bookPublisher;
    
    private String bookVersion;
    
    private String bookQuality;
    
    private String bookBoorow;


    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPhoto() {
        return bookPhoto;
    }

    public void setBookPhoto(String bookPhoto) {
        this.bookPhoto = bookPhoto;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(String bookVersion) {
        this.bookVersion = bookVersion;
    }

    public String getBookQuality() {
        return bookQuality;
    }

    public void setBookQuality(String bookQuality) {
        this.bookQuality = bookQuality;
    }

    public String getBookBoorow() {
        return bookBoorow;
    }

    public void setBookBoorow(String bookBoorow) {
        this.bookBoorow = bookBoorow;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.bookId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", userId='" + userId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookPhoto='" + bookPhoto + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", bookVersion='" + bookVersion + '\'' +
                ", bookQuality='" + bookQuality + '\'' +
                ", bookBoorow='" + bookBoorow + '\'' +
                '}';
    }
}

