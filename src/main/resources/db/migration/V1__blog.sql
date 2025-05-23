-- schema
create table blog
(
    id          bigint auto_increment primary key,
    title       varchar(255) null COMMENT '标题',
    content     text null COMMENT '正文',
    description varchar(255) null COMMENT '描述',
    cover       varchar(255) null COMMENT '封面图地址',
    created_at  datetime(6)  null COMMENT '创建时间',
    updated_at  datetime(6)  null COMMENT '更新时间'
) COMMENT '博客表';