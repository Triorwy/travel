package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);


    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUserNane(String username) {
        User user = null;
        try {
            //1、定义sql
            String sql = "select * from tab_user where username = ?";
            //2、执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);

        } catch (Exception e) {
            logger.error("查询数据异常:",e);
        }
        return user;
    }

    @Override
    public void save(User user) {

        //1:定义 sql

        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email)" +
                " values(?,?,?,?,?,?,?)";
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail());
    }
}
