create table if not exists Movies_User (
   id integer not null,
   movie_name varchar(255) not null,
   original_name varchar(255) not null,
   movie integer,
   primary key(id)
);

create table if not exists Movies (
   movie_id integer not null,
   movie_title varchar(255) not null,
   synopsis varchar(255) not null,
   release_year integer not null,
   url_cover varchar(255) not null,
   url_youtube varchar(255) not null,
   movie_user_id integer,
   primary key(movie_id)
);

INSERT INTO Movies_User (id,movie_name,original_name,movie) VALUES (1,'Iron Man','Iron Man',0);