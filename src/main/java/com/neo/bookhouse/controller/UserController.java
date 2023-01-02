/**
 * @Author: Neo
 * @Date: 2022/08/25 星期四 23:58:15
 * @Project: book_house
 * @IDE: IntelliJ IDEA
 **/
package com.neo.bookhouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.neo.bookhouse.common.R;
import com.neo.bookhouse.entity.User;
import com.neo.bookhouse.service.SendMailService;
import com.neo.bookhouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SendMailService sendMailService;

    @GetMapping("/check/{mail}")
    public R<String> checkUserName(@PathVariable String mail) {
        if (!sendMailService.isEmail(mail)) {
            return R.error("邮箱格式错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserMail, mail);
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            return R.error("该邮箱已被注册");
        }
        return R.success("该邮箱可用");
    }

    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user) {
        log.info("请求登录：{}", user);


        return R.error("登录失败");
    }


    @PostMapping("/register")//用户信息：注册账号信息
    public R<String> register(@RequestBody User user) {
        boolean flg = userService.save(user);
        if (flg) {
            log.info("注册成功：{}", user);
            return R.success("注册成功");
        }
        return R.error("注册失败");
    }


    @PutMapping
    public R<String> update(@RequestBody User user) {
        boolean flg = userService.updateById(user);
        if (flg) {
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

}
