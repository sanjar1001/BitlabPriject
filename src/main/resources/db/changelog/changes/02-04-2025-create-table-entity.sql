CREATE TABLE IF NOT EXISTS courses (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     description VARCHAR(255) NOT NULL,
     createdTime TIMESTAMP DEFAULT NOW(), --timestamp для того чтобы было сохранить с временем
     updatedTime TIMESTAMP DEFAULT NOW()  --timestamp для того чтобы было сохранить с временем
);

CREATE TABLE IF NOT EXISTS chapters (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     order_number INTEGER NOT NULL,
     course_id BIGINT NOT NULL,
     createdTime TIMESTAMP DEFAULT NOW(),--timestamp для того чтобы было сохранить с временем
     updatedTime TIMESTAMP DEFAULT NOW(),--timestamp для того чтобы было сохранить с временем
     FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS lessons (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    order_number INTEGER NOT NULL,
    chapter_id BIGINT NOT NULL,
    createdTime TIMESTAMP DEFAULT NOW(),--timestamp для того чтобы было сохранить с временем
    updatedTime TIMESTAMP DEFAULT NOW(),--timestamp для того чтобы было сохранить с временем
    FOREIGN KEY (chapter_id) REFERENCES chapters(id) ON DELETE CASCADE
);
