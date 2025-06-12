-- 之前已经发布过了的博客都给第一个用户
update user set avatar = '/img/default-avatar.png' where avatar is null;