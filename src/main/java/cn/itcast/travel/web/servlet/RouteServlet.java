package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
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
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接受rname 线路名称

        String rname = request.getParameter("rname");
        if (StringUtils.isNotEmpty(rname)) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }

        int cid = 0;//类别id
        //2.处理参数

        if (StringUtils.isNotEmpty(cidStr) && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;//当前页码，如果不传递，则默认为第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);

        //4. 将pageBean对象序列化为json，返回
        writeValue(pb, response);

    }

    /**
     * 根据id查询一个旅游线路的详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //1、接受id
        String id = request.getParameter("rid");

        //2、调用service查询route对象
        Route route = routeService.findOne(id);

        writeValue(route, response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //1、获取线路id
        String rid = request.getParameter("rid");

        //2、获取当前登陆用户user
        User user = (User) request.getSession().getAttribute("user");

        int uid = 0;
        if (user != null) {
            uid = user.getUid();
        }

        //3、写会客户端
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4、写会客户端
        writeValue(flag, response);

    }

    /**
     * 添加收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1、获取线路rid
        String rid = request.getParameter("rid");

        //2、获取当前登陆的用户
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return;
        }
        int uid = user.getUid();

        //3、调用service添加
        favoriteService.addFavorite(rid, uid);
    }
}
