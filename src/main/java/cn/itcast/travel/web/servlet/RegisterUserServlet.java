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
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:liuzhao
 * @phone:132-0145-6372
 * @date:2020/6/26 下午7:15
 */
@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUserServlet.class);

    //1.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
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
        UserService service = new UserServiceImpl();
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
}
