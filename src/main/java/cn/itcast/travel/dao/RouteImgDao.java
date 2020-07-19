package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;
import java.util.List;

/**
 * @author:liuzhao
 * @date:2020/7/18 下午5:24
 */
public interface RouteImgDao {

    public List<RouteImg> findByRid(int rid);

}
