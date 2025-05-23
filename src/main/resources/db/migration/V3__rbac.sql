-- schema

-- role
CREATE TABLE `role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(255)    DEFAULT NULL COMMENT '角色名称',
    `description` varchar(255)    DEFAULT NULL COMMENT '角色描述',
    `sort`        int    NOT NULL DEFAULT '0' COMMENT '角色顺序',
    PRIMARY KEY (`id`)
) COMMENT='角色表';


-- permission
CREATE TABLE `permission`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) DEFAULT NULL COMMENT '权限名称',
    `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
    `sort`        int    NOT NULL COMMENT '权限顺序',
    PRIMARY KEY (`id`)
) COMMENT='权限表';

-- role has permissions
create table role_permissions
(
    role_id       bigint not null,
    permission_id bigint not null,
    primary key (role_id, permission_id),
    constraint FKh0v7u4w7mttcu81o8wegayr8e
        foreign key (permission_id) references permission (id),
    constraint FKlodb7xh4a2xjv39gc3lsop95n
        foreign key (role_id) references role (id)
) COMMENT='角色和权限关联表';

-- user has roles
create table user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id) references user (id),
    constraint FKrhfovtciq1l558cw6udg0h0d3
        foreign key (role_id) references role (id)
) COMMENT='用户和角色关联表';