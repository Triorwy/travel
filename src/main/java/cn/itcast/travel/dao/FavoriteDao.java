package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * @author:liuzhao
 * @date:2020/7/19 上午9:53
 */
public interface FavoriteDao {

    public Favorite findByRidAndUid(int rid, int uid);

    public int findCountByRid(int rid);

    void add(int parseInt, int uid);
}
