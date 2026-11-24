package ltd.newbee.mall.entity;

/**
 * 商品表聚合查询的统计结果。
 */
public class GoodsStatistics {
    /** 商品表记录总数。 */
    private final long totalGoods;
    /** 处于上架状态的商品记录数。 */
    private final long onSaleGoods;

    /**
     * 创建商品统计结果。
     *
     * @param totalGoods 商品总数
     * @param onSaleGoods 上架商品数
     */
    public GoodsStatistics(long totalGoods, long onSaleGoods) {
        this.totalGoods = totalGoods;
        this.onSaleGoods = onSaleGoods;
    }

    /** @return 商品总数 */
    public long getTotalGoods() {
        return totalGoods;
    }

    /** @return 上架商品数 */
    public long getOnSaleGoods() {
        return onSaleGoods;
    }
}
