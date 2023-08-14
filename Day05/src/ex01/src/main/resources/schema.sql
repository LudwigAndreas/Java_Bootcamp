
CREATE TABLE users (
                        id BIGSERIAL PRIMARY KEY,
                        login VARCHAR(50) NOT NULL,
                        password VARCHAR(100) NOT NULL
);

CREATE TABLE chat_rooms (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            owner_id INTEGER REFERENCES users(id) NOT NULL
);

CREATE TABLE messages (
                           id BIGSERIAL PRIMARY KEY,
                           author INTEGER REFERENCES users(id) NOT NULL,
                           room_id INTEGER REFERENCES chat_rooms(id) NOT NULL ,
                           text VARCHAR(4096) NOT NULL,
                           datetime TIMESTAMP WITH TIME ZONE NOT NULL
);