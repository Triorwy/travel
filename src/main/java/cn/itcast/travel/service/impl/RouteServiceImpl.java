package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import java.util.List;

/**
 * @author:liuzhao
 * @date:2020/7/5 下午6:43
 */
public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        //封装pagebean
        PageBean<Route> pb = new PageBean<Route>();

        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid);
        pb.setTotalCount(totalCount);

        //设置当前页显示的数据集合
        //开始的记录数
        int start = (currentPage - 1) * pageSize;
        List<Route> routeList = routeDao.findBypage(cid,start,pageSize);
        pb.setList(routeList);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize  + 1);
        pb.setTotalPage(totalPage);
        return pb;
    }
}
