package ltd.newbee.mall.service;

import ltd.newbee.mall.util.PageResult;

/**
 * 会员商品收藏的读写及分页。
 */
public interface FavoriteService {
    /**
     * 查询当前会员与商品的收藏关系。
     *
     * @param userId 当前会员主键
     * @param goodsId 商品主键
     * @return 是否已收藏
     */
    boolean isFavorited(long userId, long goodsId);

    /**
     * 收藏上架商品，已有收藏时保留原记录。
     *
     * @param userId 当前会员主键
     * @param goodsId 商品主键
     * @return 收藏关系是否已建立或已存在
     */
    boolean addFavorite(long userId, long goodsId);

    /**
     * 取消当前会员的指定收藏。
     *
     * @param userId 当前会员主键
     * @param goodsId 商品主键
     */
    void removeFavorite(long userId, long goodsId);

    /**
     * 获取当前会员的最近收藏。
     *
     * @param userId 当前会员主键
     * @param requestedPage 已校验的正整数页码
     * @return 收藏分页结果
     */
    PageResult getFavoritesPage(long userId, int requestedPage);
}
