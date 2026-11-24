package ltd.newbee.mall.dao;

import ltd.newbee.mall.controller.vo.FavoriteItemVO;
import ltd.newbee.mall.entity.NewBeeMallGoodsFavorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员商品收藏关系的持久化接口。
 */
public interface NewBeeMallGoodsFavoriteMapper {
    /**
     * 查找指定会员与商品的收藏关系。
     *
     * @param userId 会员主键
     * @param goodsId 商品主键
     * @return 收藏关系，未收藏时为空
     */
    NewBeeMallGoodsFavorite findByUserAndGoods(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /**
     * 在商品上架时建立收藏关系，重复写入保留原记录。
     *
     * @param userId 会员主键
     * @param goodsId 商品主键
     * @param sellStatusUp 上架状态
     */
    void insertIfOnSale(@Param("userId") long userId, @Param("goodsId") long goodsId,
                        @Param("sellStatusUp") int sellStatusUp);

    /**
     * 删除指定会员与商品的收藏关系。
     *
     * @param userId 会员主键
     * @param goodsId 商品主键
     */
    void deleteByUserAndGoods(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /**
     * 统计会员的全部收藏关系，包含失效商品。
     *
     * @param userId 会员主键
     * @return 收藏数
     */
    int countByUser(@Param("userId") long userId);

    /**
     * 按收藏时间倒序查询会员的一页收藏。
     *
     * @param userId 会员主键
     * @param offset 跳过的记录数
     * @param pageSize 每页记录数
     * @return 当前页的收藏行
     */
    List<FavoriteItemVO> findPageByUser(@Param("userId") long userId, @Param("offset") int offset,
                                       @Param("pageSize") int pageSize);
}
