package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.controller.vo.FavoriteStatusVO;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.service.FavoriteService;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 个人中心收藏页与单件商品收藏接口。
 */
@Controller
@RequestMapping("/personal/favorites")
public class FavoriteController {
    /** 负责当前会员的收藏状态和分页查询。 */
    @Resource
    private FavoriteService favoriteService;

    /**
     * 渲染当前会员的收藏列表。
     *
     * @param page 页面传入的页码，省略时为 1
     * @param member 已登录的商城会员
     * @return 收藏列表或参数错误页面
     */
    @GetMapping
    public ModelAndView favoritesPage(@RequestParam(value = "page", required = false) String page,
                                      @SessionAttribute(Constants.MALL_USER_SESSION_KEY) NewBeeMallUserVO member) {
        int requestedPage;
        try {
            requestedPage = page == null ? 1 : Integer.parseInt(page);
        } catch (NumberFormatException invalidPage) {
            return badPage();
        }
        if (requestedPage < 1) {
            return badPage();
        }
        PageResult pageResult = favoriteService.getFavoritesPage(member.getUserId(), requestedPage);
        if (requestedPage != pageResult.getCurrPage()) {
            return new ModelAndView("redirect:/personal/favorites?page=" + pageResult.getCurrPage());
        }
        ModelAndView view = new ModelAndView("mall/my-favorites");
        view.addObject("path", "favorites");
        view.addObject("pageResult", pageResult);
        return view;
    }

    /**
     * 查询当前会员是否收藏了指定商品。
     *
     * @param goodsId 商品主键
     * @param member 已登录的商城会员
     * @return 当前收藏状态
     */
    @GetMapping("/{goodsId}")
    public ResponseEntity<Result> status(@PathVariable("goodsId") long goodsId,
                                         @SessionAttribute(Constants.MALL_USER_SESSION_KEY) NewBeeMallUserVO member) {
        if (goodsId < 1) {
            return invalidGoodsId();
        }
        return statusResponse(favoriteService.isFavorited(member.getUserId(), goodsId));
    }

    /**
     * 为当前会员添加指定商品的收藏关系。
     *
     * @param goodsId 商品主键
     * @param member 已登录的商城会员
     * @return 收藏后的状态或商品不可收藏的响应
     */
    @PutMapping("/{goodsId}")
    public ResponseEntity<Result> add(@PathVariable("goodsId") long goodsId,
                                      @SessionAttribute(Constants.MALL_USER_SESSION_KEY) NewBeeMallUserVO member) {
        if (goodsId < 1) {
            return invalidGoodsId();
        }
        if (!favoriteService.addFavorite(member.getUserId(), goodsId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ResultGenerator.genErrorResult(HttpStatus.CONFLICT.value(), "商品不存在或已下架"));
        }
        return statusResponse(true);
    }

    /**
     * 取消当前会员对指定商品的收藏。
     *
     * @param goodsId 商品主键
     * @param member 已登录的商城会员
     * @return 取消后的收藏状态
     */
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<Result> remove(@PathVariable("goodsId") long goodsId,
                                         @SessionAttribute(Constants.MALL_USER_SESSION_KEY) NewBeeMallUserVO member) {
        if (goodsId < 1) {
            return invalidGoodsId();
        }
        favoriteService.removeFavorite(member.getUserId(), goodsId);
        return statusResponse(false);
    }

    /**
     * 返回无法解析商品编号时的 JSON 响应。
     *
     * @return HTTP 400 和参数异常消息
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result> invalidGoodsId() {
        return ResponseEntity.badRequest()
                .body(ResultGenerator.genErrorResult(HttpStatus.BAD_REQUEST.value(), "商品编号无效"));
    }

    private ResponseEntity<Result> statusResponse(boolean favorited) {
        return ResponseEntity.ok(ResultGenerator.genSuccessResult(new FavoriteStatusVO(favorited)));
    }

    private ModelAndView badPage() {
        ModelAndView view = new ModelAndView("error/error_400");
        view.setStatus(HttpStatus.BAD_REQUEST);
        return view;
    }
}
