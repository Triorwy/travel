package cn.itcast.travel.service;

/**
 * @author:liuzhao
 * @date:2020/7/19 上午9:51
 */
public interface FavoriteService {

    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,int uid);

    public void addFavorite(String rid, int uid);
}
