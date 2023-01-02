package com.neo.bookhouse.entity;

import lombok.Data;

/**
 * (Booktag)实体类
 *
 * @author makejava
 * @since 2023-01-01 18:45:57
 */
@Data
public class Booktag {

    private String bookIsbn;

    private String bookKind;

    private Integer bookSum;


}

