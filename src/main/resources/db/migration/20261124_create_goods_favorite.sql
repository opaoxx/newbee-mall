CREATE TABLE IF NOT EXISTS `tb_newbee_mall_goods_favorite` (
  `favorite_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '收藏关系主键',
  `user_id` BIGINT NOT NULL COMMENT '会员主键',
  `goods_id` BIGINT UNSIGNED NOT NULL COMMENT '商品主键',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`favorite_id`),
  UNIQUE KEY `uk_user_goods` (`user_id`, `goods_id`),
  KEY `idx_user_create_time` (`user_id`, `create_time`, `favorite_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '会员商品收藏';
