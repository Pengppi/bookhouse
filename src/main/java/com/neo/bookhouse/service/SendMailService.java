/**
 * @Author: Neo
 * @Date: 2022/09/16 星期五 15:38:26
 * @Project: email
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service;


import com.neo.bookhouse.entity.MailRequest;

public interface SendMailService {

    /**
     * 简单文本邮件
     *
     * @param mailRequest
     * @return
     */
    void sendSimpleMail(MailRequest mailRequest);


    /**
     * Html格式邮件,可带附件
     *
     * @param mailRequest
     * @return
     */
    void sendHtmlMail(MailRequest mailRequest);

    void sendLoginCode(String to, String code);

    boolean isEmail(String email);
}
