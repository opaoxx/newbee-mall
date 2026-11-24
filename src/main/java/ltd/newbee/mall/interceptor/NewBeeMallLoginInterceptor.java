/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * newbee-mall系统身份验证拦截器
 *
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@Component
public class NewBeeMallLoginInterceptor implements HandlerInterceptor {
    /** 单件商品收藏接口的路径前缀。 */
    private static final String FAVORITE_ITEM_PATH_PREFIX = "/personal/favorites/";
    /** 将收藏接口的未登录错误写为项目统一的 JSON 响应。 */
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 检查商城会员 Session，并按路由返回对应的未登录响应。
     *
     * @param request 当前请求
     * @param response 当前响应
     * @param handler 当前 MVC 处理器
     * @return 已登录时允许继续处理
     * @throws Exception 响应写入失败时抛出
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Constants.MALL_USER_SESSION_KEY) != null) {
            return true;
        }
        String path = request.getServletPath();
        if (path.startsWith(FAVORITE_ITEM_PATH_PREFIX) && path.length() > FAVORITE_ITEM_PATH_PREFIX.length()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            objectMapper.writeValue(response.getOutputStream(),
                    ResultGenerator.genErrorResult(HttpServletResponse.SC_UNAUTHORIZED, "请先登录"));
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
