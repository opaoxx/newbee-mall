package ltd.newbee.mall.controller.vo;

/**
 * 后台首页展示的商城统计。
 */
public class DashboardSummaryVO {
    /** 商品表记录总数。 */
    private final long totalGoods;
    /** 在售状态的商品记录数。 */
    private final long onSaleGoods;
    /** 未注销会员数。 */
    private final long memberCount;
    /** 未删除订单数。 */
    private final long orderCount;

    /**
     * 创建后台统计数据。
     *
     * @param totalGoods 商品总数
     * @param onSaleGoods 上架商品数
     * @param memberCount 未注销会员数
     * @param orderCount 未删除订单数
     */
    public DashboardSummaryVO(long totalGoods, long onSaleGoods, long memberCount, long orderCount) {
        this.totalGoods = totalGoods;
        this.onSaleGoods = onSaleGoods;
        this.memberCount = memberCount;
        this.orderCount = orderCount;
    }

    /** @return 商品总数 */
    public long getTotalGoods() {
        return totalGoods;
    }

    /** @return 上架商品数 */
    public long getOnSaleGoods() {
        return onSaleGoods;
    }

    /** @return 未注销会员数 */
    public long getMemberCount() {
        return memberCount;
    }

    /** @return 未删除订单数 */
    public long getOrderCount() {
        return orderCount;
    }
}
