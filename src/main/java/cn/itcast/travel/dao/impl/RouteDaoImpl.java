package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author:liuzhao
 * @date:2020/7/5 下午6:22
 */
public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid) {
        String sql = "select count(rid) from tab_route where cid = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cid);
    }

    @Override
    public List<Route> findBypage(int cid, int start, int pageSize) {
        String sql = "select *  from tab_route where cid = ? limit ? ,?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class),cid,start,pageSize);
    }
}
