INSERT INTO users (login, password) VALUES
                                        ('user', 'password'),
                                        ('user1', 'securepass456'),
                                        ('user2', 'secret789'),
                                        ('user3', 'pass123'),
                                        ('user4', 'strongpass987'),
                                        ('user5', 'mypassword');

INSERT INTO chat_rooms (name, owner_id) VALUES
                                            ('General Chat', 1),
                                            ('Tech Talk', 2),
                                            ('Lounge', 3),
                                            ('Music Lovers', 4),
                                            ('Book Club', 5),
                                            ('Gaming Zone', 2);


INSERT INTO messages (author_id, room_id, message, datetime) VALUES
                                                           (1, 1, 'Hello everyone!', '2023-08-14 10:00:00+00'),
                                                           (2, 1, 'Hey there!', '2023-08-14 10:05:00+00'),
                                                           (1, 2, 'Any interesting tech news?', '2023-08-14 11:00:00+00'),
                                                           (3, 3, 'What are you all up to?', '2023-08-14 12:00:00+00'),
                                                           (3, 1, 'Just relaxing.', '2023-08-14 13:00:00+00'),
                                                           (4, 3, 'Has anyone played the latest RPG?', '2023-08-14 14:30:00+00'),
                                                           (1, 2, 'I just finished reading an amazing book!', '2023-08-14 15:00:00+00'),
                                                           (5, 1, 'Hello from a new user!', '2023-08-14 16:00:00+00'),
                                                           (2, 3, 'Any recommendations for good music?', '2023-08-14 17:00:00+00'),
                                                           (4, 2, 'What programming languages are you all into?', '2023-08-14 18:00:00+00'),
                                                           (3, 2, 'I just got a new tech gadget. Excited to try it out!', '2023-08-14 19:30:00+00'),
                                                           (1, 3, 'Anyone up for a game of chess?', '2023-08-14 20:00:00+00'),
                                                           (5, 2, 'Im learning Python programming. Any tips for beginners?', '2023-08-14 21:00:00+00'),
                                                           (2, 1, 'Whats everyones plan for the weekend?', '2023-08-15 09:00:00+00'),
                                                           (4, 3, 'Just finished a great novel. Highly recommend!', '2023-08-15 10:00:00+00'),
                                                           (3, 2, 'Tech industry is evolving so fast. Exciting times!', '2023-08-15 11:30:00+00'),
                                                           (1, 1, 'Im hosting a movie night in the lounge. Join us!', '2023-08-15 12:00:00+00'),
                                                           (5, 3, 'Thinking about starting a new game. Any suggestions?', '2023-08-15 13:00:00+00'),
                                                           (4, 1, 'Just had a delicious homemade lunch.', '2023-08-15 14:00:00+00'),
                                                           (2, 2, 'Found an interesting tech article. Sharing the link: [article-link]', '2023-08-15 15:30:00+00');
;
