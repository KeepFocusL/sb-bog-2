-- data
INSERT INTO `role` (`id`, `name`, `description`, `sort`)
VALUES
    (1, 'admin', '管理员', 0),
    (2, 'editor', '编辑', 0),
    (3, 'user', '普通用户', 0);

INSERT INTO `permission` (`id`, `name`, `description`, `sort`)
VALUES
    (1, '/backend','Dashboard__far fa-circle', 1),
    (2, '/backend/blog', '博客管理__far fa-copy', 2),
    (3, '/backend/user', '用户管理__fas fa-users', 3);

-- 给角色分配权限
INSERT INTO `role_permissions` (`role_id`, `permission_id`)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (2, 2);

-- 给用户分配角色
INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);