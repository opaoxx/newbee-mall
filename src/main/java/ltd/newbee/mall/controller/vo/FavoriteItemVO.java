package ltd.newbee.mall.controller.vo;

import ltd.newbee.mall.common.Constants;

/**
 * 收藏列表的一行及关联到的当前商品资料。
 */
public class FavoriteItemVO {
    /** 收藏关系中的商品 ID，商品失效后仍用于取消收藏。 */
    private final long goodsId;
    /** 当前商品名称，商品缺失时为空。 */
    private final String goodsName;
    /** 当前商品主图，商品缺失时为空。 */
    private final String goodsCoverImg;
    /** 当前商品售价，商品缺失时为空。 */
    private final Integer sellingPrice;
    /** 当前商品上架状态，商品缺失时为空。 */
    private final Integer goodsSellStatus;

    /**
     * 读取收藏行及可选的商品资料。
     *
     * @param goodsId 收藏关系中的商品主键
     * @param goodsName 商品名称
     * @param goodsCoverImg 商品主图
     * @param sellingPrice 商品当前售价
     * @param goodsSellStatus 商品上架状态
     */
    public FavoriteItemVO(long goodsId, String goodsName, String goodsCoverImg,
                          Integer sellingPrice, Integer goodsSellStatus) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsCoverImg = goodsCoverImg;
        this.sellingPrice = sellingPrice;
        this.goodsSellStatus = goodsSellStatus;
    }

    /** @return 收藏关系中的商品主键 */
    public long getGoodsId() {
        return goodsId;
    }

    /** @return 当前商品名称 */
    public String getGoodsName() {
        return goodsName;
    }

    /** @return 当前商品主图地址 */
    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    /** @return 当前商品售价 */
    public Integer getSellingPrice() {
        return sellingPrice;
    }

    /** @return 商品记录是否仍存在 */
    public boolean isGoodsPresent() {
        return goodsSellStatus != null;
    }

    /** @return 商品是否仍上架且可进入详情 */
    public boolean isViewable() {
        return goodsSellStatus != null && goodsSellStatus == Constants.SELL_STATUS_UP;
    }
}
