package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

/**
 * @author:liuzhao
 * @phone:132-0145-6372
 * @date:2020/6/26 下午7:14
 */
public class UserServiceImpl implements UserService {

    @Override
    public boolean regist(User user) {
        return false;
    }

    @Override
    public boolean active(String code) {
        return false;
    }

    @Override
    public User login(User user) {
        return null;
    }
}
