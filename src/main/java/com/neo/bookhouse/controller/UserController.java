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
import com.neo.bookhouse.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return R.success("该邮箱未注册");
    }

    @GetMapping("/send/{mail}")
    public R<String> sendMail(@PathVariable String mail) {
        if (!sendMailService.isEmail(mail)) {
            return R.error("邮箱格式错误");
        }
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        sendMailService.sendLoginCode(mail, code);
        log.info("验证码{}发送成功", code);
        return R.success(code);
    }


    @PostMapping("/register")//用户信息：注册账号信息
    public R<String> register(@RequestBody User user) {
        if (!sendMailService.isEmail(user.getUserMail())) {
            return R.error("邮箱格式错误");
        }
        boolean flg = userService.save(user);
        if (flg) {
            log.info("注册成功：{}", user);
            return R.success("注册成功");
        }
        return R.error("注册失败");
    }

    @PostMapping("/loginByPassword")
    public R<User> login(@RequestBody User user) {
        String mail = user.getUserMail();
        if (!sendMailService.isEmail(mail)) {
            return R.error("邮箱格式错误");
        }
        String password = user.getUserPassword();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserMail, mail);
        User one = userService.getOne(queryWrapper);
        if (one == null) {
            return R.error("该邮箱未注册");
        }
        if (!one.getUserPassword().equals(password)) {
            return R.error("密码错误");
        }
        return R.success(one);
    }

    @PostMapping("/loginByEmail")
    public R<User> loginByMail(@RequestBody User user) {
        String mail = user.getUserMail();
        if (!sendMailService.isEmail(mail)) {
            return R.error("邮箱格式错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserMail, mail);
        User one = userService.getOne(queryWrapper);
        if (one == null) {
            return R.error("该邮箱未注册");
        }
        return R.success(one);
    }

    @PostMapping("/{id}")
    public R<User> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return R.error("用户不存在");
        }
        user.setUserPassword(null);
        return R.success(user);
    }

    @PutMapping
    public R<String> update(@RequestBody User user) {
        if (user.getUserId() == null) {
            return R.error("用户id不能为空");
        }
        boolean flg = userService.updateById(user);
        if (flg) {
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        if (id == null) {
            return R.error("用户id不能为空");
        }
        boolean flg = userService.removeById(id);
        if (flg) {
            return R.success("注销成功");
        }
        return R.error("注销失败");
    }

}
