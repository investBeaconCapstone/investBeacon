# Run first three lines of code.
DROP DATABASE IF EXISTS investbeacon_db;
CREATE DATABASE investBeacon_db;

USE investBeacon_db;

# Start application then run the rest of the code.
INSERT INTO users (email, first_name, last_name, is_admin, password, username, photo)
VALUES ('noreply@investBeacon.net',
        'admin',
        'admin',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'admin', ''),
       ('jonathan@codeup.com',
        'jonathan',
        'robles',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'jonathan-robles', ''),
       ('andrawes@codeup.com',
        'andrawes',
        'batshoun',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'andrawes-batshoun', ''),
       ('aida@codeup.com',
        'aida',
        'gutierrez',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'aida-gutierrez', ''),
       ('jose@codeup.com',
        'jose',
        'diaz',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'jose-diaz', '');

INSERT INTO categories (category)
VALUES ('Crypto'),
       ('Stocks'),
       ('Finance'),
       ('Strategies'),
       ('Platforms');

INSERT INTO forum_posts(content_img_url, create_date, description, is_educational, title, user_id)
VALUES ('/image/crypto-exchange.jpeg', NOW(), 'This is a crypto example description', false,
        'Sample Crypto Title', 1),
       ('/image/stocks-example.jpeg', NOW(), 'This is a stock example description', false, 'Sample Stock Title',
        1),
       ('/image/finance-example.jpeg', NOW(), 'This is a finance example description', false,
        'Sample Finance Title', 1),
       ('/image/strategies-example.jpeg', NOW(), 'This is a strategies example description', false,
        'Sample Strategies Title', 1),
       ('/image/platforms-example.jpeg', NOW(), 'This is a platforms example description', false,
        'Sample Platforms Title', 1);

INSERT INTO forum_categories(post_id, category_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

INSERT INTO forum_posts_likes(post_id, user)
VALUES (1, 2),
       (1, 3),
       (2, 1);

INSERT INTO comments(content, create_date, post_id, user_id)
VALUES ('example of a comment', NOW(), 1, 3),
       ('example of another comment', NOW(), 2, 1);

INSERT INTO followers(user_id, follow_id)
VALUES (1,2),
       (2,1),
       (3,1),
       (1,4);

