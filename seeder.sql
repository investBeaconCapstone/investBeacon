# Run first three lines of code.
DROP DATABASE IF EXISTS investBeacon_db;
CREATE DATABASE investBeacon_db;

USE investBeacon_db;

# Start application then run the rest of the code.
INSERT INTO users (email, first_name, last_name, is_admin, password, username, photo)
VALUES ('noreply@investBeacon.net',
        'admin',
        'admin',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'admin', 'image/avatar image.png'),
       ('jonathan@codeup.com',
        'jonathan',
        'robles',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'jonathan-robles', 'image/avatar image.png'),
       ('andrawes@codeup.com',
        'andrawes',
        'batshoun',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'andrawes-batshoun', 'image/avatar image.png'),
       ('aida@codeup.com',
        'aida',
        'gutierrez',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'aida-gutierrez', 'image/avatar image.png'),
       ('jose@codeup.com',
        'jose',
        'diaz',
        true,
        '$2a$10$31kFX.gBvN6FeOtvE6i0BeOz6zi0v7xL8TbN1oABG7FoYZfa4EDV.',
        'jose-diaz', 'image/avatar image.png');

INSERT INTO categories (category)
VALUES ('Crypto'),
       ('Stocks'),
       ('Finance'),
       ('Strategies'),
       ('Platforms');

INSERT INTO forum_posts(content_img_url, create_date, description, title, user_id)
VALUES ('/image/crypto-exchange.jpeg', NOW(), 'This is a crypto example description',
        'Sample Crypto Title', 1),
       ('/image/stocks-example.jpeg', NOW(), 'This is a stock example description', 'Sample Stock Title',
        1),
       ('/image/finance-example.jpeg', NOW(), 'This is a finance example description',
        'Sample Finance Title', 1),
       ('/image/strategies-example.jpeg', NOW(), 'This is a strategies example description',
        'Sample Strategies Title', 1),
       ('/image/platforms-example.jpeg', NOW(), 'This is a platforms example description',
        'Sample Platforms Title', 1);

INSERT INTO education_post(content_img_url, created_date, description, title, cat_id, user_id)
VALUES (' ', NOW(), 'A stock is a security (a financial instrument that holds a value) represents an ownership share in a
in a publicly traded company. They are traded (bought and sold) on stock exchanges(Robin Hood, TD Ameritrade, Charles Schwab).
 When you purchase a stock, you are buying shares of the company. These shares have a price that is determined by the market(NASDAQ, S&P 500)
and will decrease or increase on the value and performance of the company.',
        'What is a stock?',2, 1),
       ('', NOW(), 'Cryptocurrency is a digital currency that relies on peer-to-peer system that can enable anyone anywhere to
send and receive payments. Since it is peer-to-peer, many types of cryptocurrencies are decentralized(The currency is not issued by a
central authority, like a bank or government), this means the value of the currency can not be influenced by any one organization.
Cryptocurrency or crypto for short, can be secured in a digital wallet and any transfer of crypto funds will recorded in a public ledger(blockchain) preventing
fraud.', 'What is Cryptocurrency', 1, 1),
       ('', now(), 'Budgeting is a topic everyone should know. It''s not something will make you more money but will allow you to determine whether you will have
enough money to do the things you need to do. Tracking where your money comes from and where it goes makes it easier to pay bills on time, build
a savings(emergency fund), saving for a home, and allows you to have better financial stability. God forbid if your vehicle breaks down or you lose your job, having a plan can set you up for success in the long run. The simplest way to start is grab a pencil and paper. Calculate your total income for the month. Next, add everything that you need to spend money on such as rent or mortgage payments, car payments, and include estimated gas and what you would spend on groceries(Don''t include stuff like netflix or going out to eat, this is for what you need). Subtract what you spend from your total income and what you have left over can be either added in savings, going out to eat, etc. This just a simple way to view where your money is going. There are many online resources(that are free) to help you create a budget, I like using Google sheets, you can create your own or using one of the many templates that have.', 'Budgets', 3, 1 ),
       ('', NOW(), 'One strategy that I would recommend beginners is Dollar-cost averaging. DCA for short, is a simple strategy where an investor would invest money in the same stock, fund, bond, or crypto on a regular basis over a period of time. For example, an investor wants to invest 100 dollars a month, and they choose stock A where the price is 5 dollars per share, the investor buys at least 20 shares every month for lets say 6 months. During the 6 months the investor purchased shares which
the price was at $5, $10, $1, $10, $1, and $5. After 6 months, the investor would have 260 shares and with the current price the total value would be 1300 dollars. The total invest was 600 dollars, subtract that from the shares it would be 700 dollars profit. It''s not bad for starting and growing your portfolio. This can be applied to crypto and mutual funds not just stocks.', 'Dollar Cost Average',4,1 );








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

