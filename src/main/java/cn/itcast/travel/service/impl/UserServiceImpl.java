package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;


public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        //1:判断用户是否已经存在
        User u = userDao.findByUserNane(user.getUsername());
        //2:判断u是否为null
        if (u != null) {
            //用户名存在
            return false;
        }
        //2、保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //3.激活邮件发送，邮件正文
        String content =
                "<a href='http://localhost:8080/travel_war_exploded/user/active?code="
                        + user.getCode() + "'>点击激活【火星陆游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);

        if(user != null){
            //调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        return userDao.findByUserNameAndPassword(user.getUsername(),user.getPassword());
    }
}
