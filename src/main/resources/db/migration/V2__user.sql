-- schema
create table user
(
    id         bigint auto_increment primary key,
    name       varchar(255) null COMMENT '用户名',
    password   varchar(255) null COMMENT '密码',
    email      varchar(255) null COMMENT '邮箱',
    mobile     varchar(255) null COMMENT '手机',
    enabled    bit(1) not null DEFAULT b'1' COMMENT '是否启用',
    created_at datetime(6)  null COMMENT '创建时间'
) COMMENT '用户表';