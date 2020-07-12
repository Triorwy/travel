package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author:liuzhao
 * @date:2020/7/5 下午5:39
 */
public interface RouteService {

    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize);
}
