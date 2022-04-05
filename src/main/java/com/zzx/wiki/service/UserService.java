package com.zzx.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzx.wiki.domain.User;
import com.zzx.wiki.domain.UserExample;
import com.zzx.wiki.mapper.UserMapper;
import com.zzx.wiki.req.UserQueryReq;
import com.zzx.wiki.req.UserSaveReq;
import com.zzx.wiki.resp.UserQueryResp;
import com.zzx.wiki.resp.PageResp;
import com.zzx.wiki.util.CopyUtil;
import com.zzx.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SnowFlake snowFlake;

    /**
     * 查询
     */
    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andNameLike("%" + req.getLoginName() + "%");
        }
        List<User> userList = userMapper.selectByExample(userExample);
        System.out.println(userList);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());


//        List<UserResp> respList = new ArrayList<>();
//        for (User user : userList) {
////            UserResp userResp = new UserResp();
////            BeanUtils.copyProperties(user, userResp);
        //对象复制
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            respList.add(userResp);
//        }

        //列表复制
        List<UserQueryResp> respList = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            //新增
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        } else {
            //编辑
            userMapper.updateByPrimaryKey(user);
        }
    }

    /**
     * 删除
     */
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
