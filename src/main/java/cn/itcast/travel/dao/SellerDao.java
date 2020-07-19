package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author:liuzhao
 * @date:2020/7/18 下午5:38
 */
public interface SellerDao {

    public Seller findById(int id);
}
