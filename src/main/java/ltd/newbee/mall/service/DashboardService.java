package ltd.newbee.mall.service;

import ltd.newbee.mall.controller.vo.DashboardSummaryVO;

/**
 * 后台基础统计服务。
 */
public interface DashboardService {
    /**
     * 查询后台首页的四项基础统计。
     *
     * @return 当前统计结果
     */
    DashboardSummaryVO getSummary();
}
