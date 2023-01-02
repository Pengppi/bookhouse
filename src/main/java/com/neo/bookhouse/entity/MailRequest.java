/**
 * @Author: Neo
 * @Date: 2022/09/16 星期五 15:39:13
 * @Project: email
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.entity;


import lombok.Data;

import java.io.Serializable;


@Data
public class MailRequest implements Serializable {
    /**
     * 接收人
     */
    private String sendTo;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 附件路径
     */
    private String filePath;

}


