package com.neo.bookhouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Code {

    //BigDecimal
    private String codeId;

    private Long bookId;

    private Integer codeType;

    private LocalDateTime codeStart;

    private LocalDateTime codeEnd;

    private Integer codeState;


}
