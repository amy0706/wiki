package com.zzx.wiki.controller;

import com.zzx.wiki.domain.User;
import com.zzx.wiki.req.UserLoginReq;
import com.zzx.wiki.req.UserQueryReq;
import com.zzx.wiki.req.UserResetPasswordReq;
import com.zzx.wiki.req.UserSaveReq;
import com.zzx.wiki.resp.CommonResp;
import com.zzx.wiki.resp.UserLoginResp;
import com.zzx.wiki.resp.UserQueryResp;
import com.zzx.wiki.resp.PageResp;
import com.zzx.wiki.service.UserService;
import com.zzx.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Valid UserSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@RequestBody @Valid UserResetPasswordReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.resetPassword(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@RequestBody @Valid UserLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(req);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token:{}，并放入redis中", token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token, userLoginResp, 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(userLoginResp);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token:{}", token);
        return resp;
    }

}
