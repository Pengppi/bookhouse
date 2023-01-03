package com.neo.bookhouse.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * (Borrow)实体类
 *
 * @author makejava
 * @since 2023-01-01 18:44:21
 */
@Data
@Builder
public class Borrow {

    private Long userId;
    
    private LocalDateTime borrowDate;
    
    private Long bookId;
    
    private LocalDateTime shouldDate;
    
    private LocalDateTime returnDate;
    
    private Integer borrowState;


}

