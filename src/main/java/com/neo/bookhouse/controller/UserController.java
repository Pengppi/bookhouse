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

    @GetMapping("/{id}")//用户信息：检查账号是否存在
    public R<String> checkUserId(@PathVariable String id) {
        log.info("id={}", id);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, id);
        User one = userService.getOne(queryWrapper);
        if (one != null) {
            return R.error("");
        }
        return R.success("");
    }

    @PostMapping
    public R<User> login(HttpServletRequest request, @RequestBody User user) {
        //1.将页面提交的密码进行md5加密处理
        String password = user.getUserPassword();
        password = md5(password);
        //2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, user.getUserId());
        User emp = userService.getOne(queryWrapper);
        //3.如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("账号不存在");
        }
        //4.密码比对，如果不一致则返回登陆失败结果
        if (!emp.getUserPassword().equals(password)) {
            return R.error("密码错误");
        }
        //5.登录成功，将id存入session并返回登录成功结果
        request.getSession().setAttribute("user", emp.getUserId());
        emp.setUserPassword(null);
        return R.success(emp);
    }


    @PostMapping("/register")//用户信息：注册账号信息
    public R<String> register(@RequestBody User user) {
        if (user.getUserId().trim().length() == 0) {
            return R.error("账号不能为空");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, user.getUserId());
        User one = userService.getOne(queryWrapper);
        if (one != null) {
            return R.error("账号已存在");
        }
        String pwd = user.getUserPassword().trim();
        user.setUserPassword(md5(pwd));
        boolean flg = userService.save(user);
        if (flg) {
            return R.success("注册成功");
        }
        return R.error("注册失败");
    }


    @PutMapping
    public R<String> update(@RequestBody User user) {
        //boolean flg = userService.updateById(user);
        //if (flg) {
        return R.success("修改成功");
        //}
        //return R.error("修改失败");
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
