package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.dao.NewBeeMallGoodsFavoriteMapper;
import ltd.newbee.mall.service.FavoriteService;
import ltd.newbee.mall.util.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    /** 收藏列表的固定每页条数。 */
    private static final int PAGE_SIZE = 10;
    /** 查询和修改当前会员的收藏关系。 */
    @Resource
    private NewBeeMallGoodsFavoriteMapper favoriteMapper;

    /** {@inheritDoc} */
    @Override
    public boolean isFavorited(long userId, long goodsId) {
        return favoriteMapper.findByUserAndGoods(userId, goodsId) != null;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public boolean addFavorite(long userId, long goodsId) {
        // 1. 只为上架商品创建收藏，唯一约束处理重复和并发请求。
        favoriteMapper.insertIfOnSale(userId, goodsId, Constants.SELL_STATUS_UP);
        // 2. 检查关系的最终状态，已有收藏在商品下架或失效后仍然有效。
        return isFavorited(userId, goodsId);
    }

    /** {@inheritDoc} */
    @Override
    public void removeFavorite(long userId, long goodsId) {
        favoriteMapper.deleteByUserAndGoods(userId, goodsId);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public PageResult getFavoritesPage(long userId, int requestedPage) {
        // 1. 用当前会员的收藏总数识别空列表。
        int totalCount = favoriteMapper.countByUser(userId);
        if (totalCount == 0) {
            return new PageResult(Collections.emptyList(), 0, PAGE_SIZE, 1);
        }
        // 2. 取消末页最后一项时，回到新的末页。
        int lastPage = (totalCount - 1) / PAGE_SIZE + 1;
        int currentPage = Math.min(requestedPage, lastPage);
        int offset = (currentPage - 1) * PAGE_SIZE;
        // 3. 从同一事务快照中读取该页商品资料。
        return new PageResult(favoriteMapper.findPageByUser(userId, offset, PAGE_SIZE),
                totalCount, PAGE_SIZE, currentPage);
    }
}
