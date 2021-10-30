DROP TABLE crypto_history IF EXISTS;
CREATE TABLE crypto_history (
    id long PRIMARY KEY AUTO_INCREMENT,
    amount decimal(19,6) NOT NULL,
    datetime datetime NOT NULL
);
INSERT INTO crypto_history(amount, datetime) VALUES (1000, '2019-10-05T13:00:00+00:00');
INSERT INTO crypto_history(amount, datetime) VALUES (1001, '2019-10-05T14:00:00+00:00');
INSERT INTO crypto_history(amount, datetime) VALUES (1002, '2019-10-05T15:00:00+00:00');
INSERT INTO crypto_history(amount, datetime) VALUES (1003, '2019-10-05T16:00:00+00:00');
INSERT INTO crypto_history(amount, datetime) VALUES (1004, '2019-10-05T17:00:00+00:00');