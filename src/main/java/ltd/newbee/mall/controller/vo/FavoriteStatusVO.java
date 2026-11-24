package ltd.newbee.mall.controller.vo;

/**
 * 当前会员针对一件商品的收藏状态。
 */
public class FavoriteStatusVO {
    /** 当前会员是否保有此收藏关系。 */
    private final boolean favorited;

    /**
     * 构造状态查询或更新结果。
     *
     * @param favorited 是否已收藏
     */
    public FavoriteStatusVO(boolean favorited) {
        this.favorited = favorited;
    }

    /** @return 是否已收藏 */
    public boolean isFavorited() {
        return favorited;
    }
}
