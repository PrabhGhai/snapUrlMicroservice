ALTER TABLE urls
ADD CONSTRAINT uk_user_longurl UNIQUE (user_id, long_url);
