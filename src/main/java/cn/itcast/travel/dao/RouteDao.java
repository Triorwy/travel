package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import java.util.List;

/**
 * @author:liuzhao
 * @date:2020/7/5 下午6:01
 */
public interface RouteDao {

    /**
     * 根据cid查询总记录数
     */

    public int findTotalCount(int cid);

    /**
     * 根据cid，start，pageSize查询当前页的数据集合
     */

    public List<Route> findBypage(int cid, int start, int pageSize);

}
