CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR(100),
    login VARCHAR(100),
    name VARCHAR(100),
    birthday TIMESTAMP
);

CREATE TABLE IF NOT EXISTS friends (
    user_id BIGINT,
    friend_id BIGINT,
    status BOOLEAN
);

//INSERT INTO users (id, email, login, name)
//VALUES ( 111, 'user1@email.ru', 'user1', 'user1' );