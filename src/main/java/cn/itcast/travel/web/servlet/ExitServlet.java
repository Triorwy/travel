package cn.itcast.travel.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:liuzhao
 * @date:2020/7/4 下午3:48
 */

@WebServlet("/exitServlet")
public class ExitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //1.销毁session
        req.getSession().invalidate();

        //2.跳转登陆页面

        resp.sendRedirect(req.getContextPath() + "/login.html");

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPut(req, resp);
    }
}
