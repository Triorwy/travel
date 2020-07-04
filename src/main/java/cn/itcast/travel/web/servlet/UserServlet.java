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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:liuzhao
 * @date:2020/7/4 下午4:34
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet{

    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    private static final String USER_STATUS = "Y";

    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regiser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //验证码校验 前台提交的验证码
        String check = req.getParameter("check");

        logger.info("验证码：{}",check);
        //从session中获取验证码
        HttpSession session = req.getSession();

        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //清除session，验证码只用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;
        }
        //1、获取数据
        Map<String, String[]> map = req.getParameterMap();

        //2、封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            logger.error("封装对象异常1：", e);
        } catch (InvocationTargetException e) {
            logger.error("封装对象异常2：", e);
        }
        //3、调用service完成注册
        //UserService service = new UserServiceImpl();
        boolean flag = service.register(user);
        ResultInfo resultInfo = new ResultInfo();
        //4、响应结果
        if (flag) {
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败:用户名已存在，请直接登录");
        }
        //5、将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);

        //6、将json数据写会客户端
        //设置content-type
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);

    }

    /**
     * 登陆功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp)
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

//        UserService userService = new UserServiceImpl();
        User loginUser = service.login(user);

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

    /**
     * 查找显示用户功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //从session中获取登陆用户

        System.out.println("userServlet的add");
        Object user = req.getSession().getAttribute("user");

        //将user写回客户端

        ObjectMapper mapper = new ObjectMapper();

        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(), user);
    }


    /**
     * 推出功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //1.销毁session
        req.getSession().invalidate();

        //2.跳转登陆页面

        resp.sendRedirect(req.getContextPath() + "/login.html");
    }

    /**
     * 激活功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //1.获取激活码

        String code = req.getParameter("code");
        if (code != null) {
            boolean active = service.active(code);
            String msg = null;
            if (active) {
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                msg = "激活失败，请联系管理员！";
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);
        }

    }

}
