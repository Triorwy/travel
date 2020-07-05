package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;

/**
 * @author:liuzhao
 * @date:2020/7/4 下午5:34
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis中查询

        //1.1 获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();

        //1.2 使用sortedset排序查询，为了分类栏展示顺便按照一定顺序 0到-1代表查询全部
        Set<String> categorys = jedis.zrange("category", 0, -1);
        List<Category> cs = null;

        //2.判断查询的集合是否为空
        if(categorys == null || categorys.size() == 0){
            System.out.println("mysql");
            //3.如果为空，需要从数据库查询，将数据库存入redis中
            //3.1:从数据库查询
            cs = categoryDao.findAll();
            //3.2将集合保存到redis中进行缓存，category作为key
            for(int i = 0;i < cs.size();i++){
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            //将set数据存入list
            System.out.println("redis");
            cs = new ArrayList<Category>();
            for(String category : categorys){
                Category ca = new Category();
                ca.setCname(category);
                cs.add(ca);
            }
        }
        //4 如果不为空，直接返回
        return cs;
    }
}
