CREATE TABLE `password_reset_token`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT,
    `token`           varchar(255) NOT NULL COMMENT '重置密码 token',
    `user_id`         bigint DEFAULT NULL COMMENT 'token 所属的用户 ID',
    `expiration_date` datetime(6) NOT NULL COMMENT '过期时间',
    `created_at` datetime(6) NOT NULL COMMENT '生成时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`token`),
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);