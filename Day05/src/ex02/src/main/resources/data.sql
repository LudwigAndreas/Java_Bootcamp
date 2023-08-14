INSERT INTO users (login, password) VALUES
                                        ('user', 'password'),
                                        ('user1', 'securepass456'),
                                        ('user2', 'secret789');

INSERT INTO chat_rooms (name, owner_id) VALUES
                                            ('General Chat', 1),
                                            ('Tech Talk', 2),
                                            ('Lounge', 3);


INSERT INTO messages (author_id, room_id, message, datetime) VALUES
                                                           (1, 1, 'Hello everyone!', '2023-08-14 10:00:00+00'),
                                                           (2, 1, 'Hey there!', '2023-08-14 10:05:00+00'),
                                                           (1, 2, 'Any interesting tech news?', '2023-08-14 11:00:00+00'),
                                                           (3, 3, 'What are you all up to?', '2023-08-14 12:00:00+00'),
                                                           (3, 1, 'Just relaxing.', '2023-08-14 13:00:00+00');
