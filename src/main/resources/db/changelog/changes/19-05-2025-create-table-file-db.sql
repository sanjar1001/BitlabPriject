--liquibase formatted sql
--changeset sanzhar:6
CREATE TABLE attachments (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  url VARCHAR(255),
  lesson_id BIGINT NOT NULL,
  created_time TIMESTAMP,
  FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE

);

