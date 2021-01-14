package com.zh.android.todolist.server.controller;

import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RestController;
import com.zh.android.json.JSONUtil;
import com.zh.android.todolist.server.model.User;

/**
 * @author wally
 * @date 2021/01/06
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/info")
    public String info() {
        User user = new User(1, "hezihao", "123");
        return JSONUtil.toJsonStr(user);
    }
}