/**
 * @Author: Neo
 * @Date: 2022/09/16 星期五 15:38:42
 * @Project: email
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.service.impl;


import com.neo.bookhouse.entity.MailRequest;
import com.neo.bookhouse.service.SendMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SendMailServiceImpl implements SendMailService {

    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);
    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    public void checkMail(MailRequest mailRequest) {
        Assert.notNull(mailRequest, "邮件请求不能为空");
        Assert.notNull(mailRequest.getSendTo(), "邮件收件人不能为空");
        Assert.notNull(mailRequest.getSubject(), "邮件主题不能为空");
        Assert.notNull(mailRequest.getText(), "邮件收件人不能为空");
    }

    @Override
    public void sendSimpleMail(MailRequest mailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        checkMail(mailRequest);
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(mailRequest.getSendTo().split(","));
        //邮件主题
        message.setSubject(mailRequest.getSubject());
        //邮件内容
        message.setText(mailRequest.getText());
        //邮件发送时间
        message.setSentDate(new Date());

        javaMailSender.send(message);
        logger.info("发送邮件成功:{}->{}", sendMailer, mailRequest.getSendTo());
    }


    @Override
    public void sendHtmlMail(MailRequest mailRequest) {
        MimeMessage message = javaMailSender.createMimeMessage();
        checkMail(mailRequest);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //邮件发件人
            helper.setFrom(sendMailer);
            //邮件收件人 1或多个
            helper.setTo(mailRequest.getSendTo().split(","));
            //邮件主题
            helper.setSubject(mailRequest.getSubject());
            //邮件内容
            helper.setText(mailRequest.getText(), true);
            //邮件发送时间
            helper.setSentDate(new Date());

            String filePath = mailRequest.getFilePath();
            if (StringUtils.hasText(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
            }
            javaMailSender.send(message);
            logger.info("发送邮件成功:{}->{}", sendMailer, mailRequest.getSendTo());
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }

    @Override
    public void sendLoginCode(String to, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //邮件发件人
            helper.setFrom(sendMailer);
            //邮件收件人 1或多个
            helper.setTo(to);
            //邮件主题
            helper.setSubject("西山对寂寥");
            //邮件内容
            helper.setText("<head>\n" +
                    "    <base target=\"_blank\" />\n" +
                    "    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>\n" +
                    "    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>\n" +
                    "    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n" +
                    "    <style type=\"text/css\">\n" +
                    "        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n" +
                    "        td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}\n" +
                    "        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n" +
                    "        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n" +
                    "        img{ border:0}\n" +
                    "        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n" +
                    "        blockquote{margin-right:0px}\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body tabindex=\"0\" role=\"listitem\">\n" +
                    "<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n" +
                    "    <tbody>\n" +
                    "    <tr>\n" +
                    "        <td>\n" +
                    "            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n" +
                    "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n" +
                    "                    <tbody><tr><td width=\"210\"></td></tr></tbody>\n" +
                    "                </table>\n" +
                    "            </div>\n" +
                    "            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n" +
                    "                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n" +
                    "                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong>\n" +
                    "                    <strong style=\"display:block;margin-bottom:15px;\">\n" +
                    "                        您正在进行<span style=\"color: red\">登录或注册账号</span>操作，请在验证码输入框中输入：<span style=\"color:#f60;font-size: 24px\">" + code + "</span>，以完成操作。\n" +
                    "                    </strong>\n" +
                    "                </div>\n" +
                    "                <div style=\"margin-bottom:30px;\">\n" +
                    "                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n" +
                    "                        <p style=\"color:#747474;\">\n" +
                    "                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n" +
                    "                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n" +
                    "                        </p>\n" +
                    "                    </small>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div style=\"width:700px;margin:0 auto;\">\n" +
                    "                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
                    "                    <p>此为系统邮件，请勿回复<br>\n" +
                    "                        请保管好您的邮箱，避免账号被他人盗用\n" +
                    "                    </p>\n" +
                    "                    <p>西山对寂寥网络科技团队</p>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "        </td>\n" +
                    "    </tr>\n" +
                    "    </tbody>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "\n", true);
            //邮件发送时间
            helper.setSentDate(new Date());
            javaMailSender.send(message);
            logger.info("发送邮件成功:{}->{}", sendMailer, to);
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！", e);
        }
    }

    @Override
    public boolean isEmail(String email) {
        if (null == email || "".equals(email)) {
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        }
        return false;
    }
}


