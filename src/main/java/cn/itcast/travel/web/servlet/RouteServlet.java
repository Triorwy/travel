package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @author:liuzhao
 * @date:2020/7/5 下午5:28
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{

    private RouteService routeService = new RouteServiceImpl();

    /**
     * 分页查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //1、接受参数
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");

        //接受rname 线路名称
//        String rname = req.getParameter("rname");
//        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        //类别id
        int cid = 0;
        if(StringUtils.isNotEmpty(cidStr)){
            cid = Integer.valueOf(cidStr);
        }

        //当前页码，如果不传递则默认为第一页
        int currentPage = 0;
        if(StringUtils.isNotEmpty(currentPageStr)){
            currentPage = Integer.valueOf(currentPageStr);
        }else{
            currentPage = 1;
        }

        //每页显示条数，默认显示五条
        int pageSize = 0;
        if(StringUtils.isNotEmpty(pageSizeStr)){
            pageSize = Integer.valueOf(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize);

        //4. 将pageBean对象序列化为json，返回
        writeValue(pb,resp);
    }
}
