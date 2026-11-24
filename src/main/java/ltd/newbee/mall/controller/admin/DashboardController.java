package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.service.DashboardService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DashboardController {
    /** 为后台首页和接口提供统一口径的统计数据。 */
    @Resource
    private DashboardService dashboardService;

    /**
     * 返回已登录管理员可查看的基础统计数据。
     *
     * @return 包含四项统计的响应
     */
    @GetMapping("/admin/dashboard/summary")
    public Result summary() {
        return ResultGenerator.genSuccessResult(dashboardService.getSummary());
    }
}
