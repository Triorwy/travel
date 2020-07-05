package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import java.util.List;

/**
 * @author:liuzhao
 * @date:2020/7/4 下午5:34
 */
public interface CategoryService {

    public List<Category> findAll();
}
