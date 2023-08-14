
CREATE TABLE users (
                        id BIGSERIAL PRIMARY KEY,
                        login VARCHAR(50) UNIQUE NOT NULL,
                        password VARCHAR(100) NOT NULL
);

CREATE TABLE chat_rooms (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            owner_id INTEGER REFERENCES users(id) NOT NULL
);

CREATE TABLE messages (
                           id BIGSERIAL PRIMARY KEY,
                           author_id INTEGER REFERENCES users(id) NOT NULL,
                           room_id INTEGER REFERENCES chat_rooms(id) NOT NULL ,
                           message VARCHAR(4096) NOT NULL,
                           datetime TIMESTAMP WITH TIME ZONE
);

-- CTE for user pagination with chatrooms created by user and chatrooms where user is participating
-- WITH user_pagination AS (
--     SELECT
--         id,
--         login,
--         password,
--         ROW_NUMBER() OVER (ORDER BY id) AS row_num
--     FROM users
-- ),
--      UserChats AS (
--          SELECT
--              u.id as user_id,
--              c.id as chat_id,
--              c.name,
--              c.owner_id
--          FROM user_pagination u
--                   LEFT JOIN chat_rooms c ON u.id = c.owner_id
--      ),
--      UserParticipatingChats AS (
--          SELECT
--              u.id as user_id,
--              c.id as chat_id,
--              c.name,
--              c.owner_id
--          FROM user_pagination u
--                   JOIN messages m ON u.id = m.author_id
--                   JOIN chat_rooms c ON m.room_id = c.id
--      )
-- SELECT
--     up.id,
--     up.login,
--     up.password,
--     ARRAY_AGG(DISTINCT uc.chat_id || ':' || uc.name || ':' || uc.owner_id) AS created_chats,
--     ARRAY_AGG(DISTINCT upc.chat_id || ':' || upc.name || ':' || upc.owner_id) AS participating_chats
-- FROM user_pagination up
--          LEFT JOIN UserChats uc ON up.id = uc.user_id
--          LEFT JOIN UserParticipatingChats upc ON up.id = upc.user_id
-- WHERE up.row_num BETWEEN ((1 - 1) * 4) + 1 AND (1 * 4)
-- GROUP BY up.id, up.login, up.password;