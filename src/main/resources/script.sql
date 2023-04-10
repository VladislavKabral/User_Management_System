create table Users (
   user_id int primary key auto_increment,
   user_lastname varchar(20) not null,
   user_firstname varchar(20) not null,
   user_name varchar(20) not null,
   user_email varchar(50) not null,
   user_date_of_birthday date not null,
   user_state varchar(10) not null,
   user_created_at datetime not null
);