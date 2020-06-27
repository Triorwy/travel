package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

/**
 * @author:liuzhao
 * @phone:132-0145-6372
 * @date:2020/6/26 下午7:14
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        //1:判断用户是否已经存在
        User u = userDao.findByUserNane(user.getUsername());
        //2:判断u是否为null
        if(u != null){
            //用户名存在
            return false;
        }
        //保存用户信息
        userDao.save(user);
        return true;
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
