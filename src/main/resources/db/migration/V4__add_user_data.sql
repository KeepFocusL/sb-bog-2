-- data
INSERT INTO `user` (`id`, `name`, `password`, `email`, `mobile`, `enabled`)
VALUES (1, 'admin', '$2a$10$X/uMNuiis.fyO47cxbta3OSs2sllSeLcwVfC0.ghyxeVVZRmAbzk2', 'admin@example.com', NULL, b'1'),
       (2, 'editor', '$2a$10$X/uMNuiis.fyO47cxbta3OSs2sllSeLcwVfC0.ghyxeVVZRmAbzk2', 'editor@example.com', NULL, b'1'),
       (3, 'user', '$2a$10$X/uMNuiis.fyO47cxbta3OSs2sllSeLcwVfC0.ghyxeVVZRmAbzk2', 'user@example.com', NULL, b'1');