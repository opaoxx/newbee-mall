package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.controller.vo.DashboardSummaryVO;
import ltd.newbee.mall.dao.MallUserMapper;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.dao.NewBeeMallOrderMapper;
import ltd.newbee.mall.entity.GoodsStatistics;
import ltd.newbee.mall.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class DashboardServiceImpl implements DashboardService {
    /** 查询商品总数及上架商品数。 */
    @Resource
    private NewBeeMallGoodsMapper goodsMapper;
    /** 查询未注销会员数。 */
    @Resource
    private MallUserMapper userMapper;
    /** 查询未删除订单数。 */
    @Resource
    private NewBeeMallOrderMapper orderMapper;

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public DashboardSummaryVO getSummary() {
        // 1. 在同一次商品聚合中计算商品总数和上架商品数。
        GoodsStatistics goodsStatistics = goodsMapper.countGoodsStatistics(Constants.SELL_STATUS_UP);
        // 2. 从同一事务快照中查询未注销会员和未删除订单。
        long memberCount = userMapper.countNonDeletedMembers();
        long orderCount = orderMapper.countNonDeletedOrders();
        // 3. 为后台首页和 JSON 接口组装同一份展示数据。
        return new DashboardSummaryVO(goodsStatistics.getTotalGoods(), goodsStatistics.getOnSaleGoods(),
                memberCount, orderCount);
    }
}
