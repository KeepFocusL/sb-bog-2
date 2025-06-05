-- 之前已经发布过了的博客都给第一个用户
update blog set user_id = 1 where user_id is null;