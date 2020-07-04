package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:liuzhao
 * @date:2020/6/27 下午6:00
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private static final String USER_STATUS = "Y";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //1.获取用户和密码
        Map<String, String[]> parameterMap = req.getParameterMap();

        //2.封装user对象
        User user = new User();

        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            logger.error("用户登陆封装异常：{}", e);
        } catch (InvocationTargetException e) {
            logger.error("用户登陆封装异常2：{}", e);
        }

        //3.调用service查询

        UserService userService = new UserServiceImpl();
        User loginUser = userService.login(user);

        //4.判断登陆信息
        ResultInfo resultInfo = new ResultInfo();
        if (loginUser == null) {
            //用户名密码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }
        //5.判断用户是否激活
        if (loginUser != null && !USER_STATUS.equalsIgnoreCase(loginUser.getStatus())) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("您尚未激活，请先激活！");
        }
        //6.判断登陆成功
        if (loginUser != null && USER_STATUS.equalsIgnoreCase(loginUser.getStatus())) {
            resultInfo.setFlag(true);
            req.getSession().setAttribute("user",loginUser);//登录成功标记
        }
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");

        mapper.writeValue(resp.getOutputStream(), resultInfo);
    }
}
