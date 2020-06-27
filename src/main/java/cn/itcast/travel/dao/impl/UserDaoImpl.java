package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {


    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User findByCode(String code) {
        return null;
    }

    @Override
    public void updateStatus(User user) {

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return null;
    }
}
