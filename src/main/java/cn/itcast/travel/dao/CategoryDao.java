package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import java.util.List;

/**
 * @author:liuzhao
 * @date:2020/7/4 下午5:29
 */
public interface CategoryDao {

    public List<Category> findAll();
}
