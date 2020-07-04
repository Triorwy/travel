package cn.itcast.travel.web.servlet;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:liuzhao
 * @date:2020/7/4 下午4:07
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //完成方法的分发
        //1、获取请求路径
        String requestURI = req.getRequestURI();
        System.out.println(requestURI);

        //2、获取方法名称

        String methodName = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        System.out.println(methodName);
        //3、获取方法对象method
        try {
            //忽略访问权限的控制：getDeclaredMethod
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this, req, resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //4、执行方法

    }
}
