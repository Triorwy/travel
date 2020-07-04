package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:liuzhao
 * @date:2020/7/4 下午3:19
 */
@WebServlet("/findUserServlet")
public class FindUserServlet extends BaseServlet {


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //从session中获取登陆用户

        System.out.println("userServlet的add");
        Object user = req.getSession().getAttribute("user");

        //将user写回客户端

        ObjectMapper mapper = new ObjectMapper();

        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(), user);
    }

}
