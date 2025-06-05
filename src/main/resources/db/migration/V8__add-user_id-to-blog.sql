alter table blog
    add user_id bigint null comment '博客关联的用户 id';

alter table blog
    add constraint blog_user_id_fk
        foreign key (user_id) references user (id);