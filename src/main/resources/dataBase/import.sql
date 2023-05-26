insert into CATEGORIES (ID, NAME) values (1, 'Application & Services');
insert into CATEGORIES (ID, NAME) values (2, 'Benefits & Paper Work');
insert into CATEGORIES (ID, NAME) values (3, 'Hardware & Software');
insert into CATEGORIES (ID, NAME) values (4, 'People Management');
insert into CATEGORIES (ID, NAME) values (5, 'Security & Access');
insert into CATEGORIES (ID, NAME) values (6, 'Workplaces & Facilities');

insert into USERS (ID, EMAIL,  FIRST_NAME, LAST_NAME, PASSWORD,  ROLE_ID) values (1, 'e1@yopmail.com', 'Adam', 'Warlock', '{bcrypt}$2a$10$ObglJMFhSDX9dmCkNdXfauH7/2qyVyDUfJpkKQQ.szSSRXxUZCaO6', 0);
insert into USERS (ID, EMAIL,  FIRST_NAME, LAST_NAME, PASSWORD,  ROLE_ID) values (2, 'e2@yopmail.com', 'Bucky', 'Barnes', '{bcrypt}$2a$10$uuOY1CZtGTwjez8DwDLWyeuZhkAhcWaigWQMgN6gTJQEFwtg0qq9G', 0);
insert into USERS (ID, EMAIL,  FIRST_NAME, LAST_NAME, PASSWORD,  ROLE_ID) values (3, 'm1@yopmail.com', 'Wade', 'Wilson', '{bcrypt}$2a$10$gyg4.uBbUW3YWKWyhk54gelNegh/xoFRJ/tor2Z8jmJr69MvjuKjO', 1);
insert into USERS (ID, EMAIL,  FIRST_NAME, LAST_NAME, PASSWORD,  ROLE_ID) values (4, 'm2@yopmail.com', 'Eric', 'Brooks', '{bcrypt}$2a$10$aQu2O6hoTYi8VOk/b6KWb.xBCaYa7R6JIzP5qzUOyU8eBKvSWs6Mm', 1);
insert into USERS (ID, EMAIL,  FIRST_NAME, LAST_NAME, PASSWORD,  ROLE_ID) values (5, 'a1@yopmail.com', 'Scott', 'Lang', '{bcrypt}$2a$10$CtcmUCx.1lVcS7c88PJju.gI45CDzR8VcdH1WqnacTG22awsF8lK2', 2);
insert into USERS (ID, EMAIL,  FIRST_NAME, LAST_NAME, PASSWORD,  ROLE_ID) values (6, 'a2@yopmail.com', 'Mariko', 'Yashida', '{bcrypt}$2a$10$QeuKfoyaTa7LghTSqNieluhTFyxW6pYoDm7cHOQANduy9OIYFZPCO', 2);
