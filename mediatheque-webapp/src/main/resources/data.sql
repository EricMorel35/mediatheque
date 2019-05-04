/*create table if not exists Movie_User (
   id integer not null,
   movie_name varchar(255) not null,
   original_name varchar(255) not null,
   movie integer,
   primary key(id)
);

create table if not exists Movie (
   movie_id integer not null,
   movie_title varchar(255) not null,
   synopsis clob not null,
   release_year integer not null,
   url_cover varchar(255) not null,
   url_youtube varchar(255) not null,
   movie_user_id integer,
   primary key(movie_id)
);

create table if not exists Movie_Kind (
   kind varchar(50) not null,
);

INSERT INTO Movie_User (id,movie_name,original_name,movie) VALUES (1,'Iron Man','Iron Man',1726);
INSERT INTO Movie_User (id,movie_name,original_name,movie) VALUES (2,'Taxi','Taxi',2330);
INSERT INTO Movie_User (id,movie_name,original_name,movie) VALUES (3,'Inglorious Basterds','Inglorious Basterds',16869);*/