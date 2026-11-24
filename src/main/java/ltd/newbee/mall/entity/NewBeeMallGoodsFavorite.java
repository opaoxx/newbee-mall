package ltd.newbee.mall.entity;

import java.util.Date;

/**
 * 已持久化的会员商品收藏关系。
 */
public class NewBeeMallGoodsFavorite {
    /** 收藏关系主键。 */
    private final long favoriteId;
    /** 执行收藏的会员主键。 */
    private final long userId;
    /** 被收藏的商品主键。 */
    private final long goodsId;
    /** 这次收藏关系建立的时间。 */
    private final Date createTime;

    /**
     * 从数据库读取一条收藏关系。
     *
     * @param favoriteId 收藏关系主键
     * @param userId 会员主键
     * @param goodsId 商品主键
     * @param createTime 收藏时间
     */
    public NewBeeMallGoodsFavorite(long favoriteId, long userId, long goodsId, Date createTime) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.goodsId = goodsId;
        this.createTime = createTime;
    }

    /** @return 收藏关系主键 */
    public long getFavoriteId() {
        return favoriteId;
    }

    /** @return 会员主键 */
    public long getUserId() {
        return userId;
    }

    /** @return 商品主键 */
    public long getGoodsId() {
        return goodsId;
    }

    /** @return 收藏时间 */
    public Date getCreateTime() {
        return createTime;
    }
}
