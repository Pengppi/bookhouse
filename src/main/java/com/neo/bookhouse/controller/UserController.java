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

    @GetMapping("/check/{userName}")
    public R<String> checkUserName(@PathVariable String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            return R.error("用户名已存在");
        }
        return R.success("用户名可用");
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


    public R<String> upload(MultipartFile file) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        log.info(fileName);
        //file.transferTo();

        return R.success("上传成功");
    }


    /**
     * md5加密
     *
     * @param pwd
     * @return
     */
    public String md5(String pwd) {
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        return pwd;
    }


}
