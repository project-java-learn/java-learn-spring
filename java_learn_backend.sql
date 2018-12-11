create table progress (progress_id varchar(255) for bit data not null,
  score integer not null,
  updated timestamp not null,
  user_field varchar(255) for bit data not null, primary key (progress_id))

create table progress_levels (progress_progress_id varchar(255) for bit data not null,
  levels varchar(255))

create table user_table (user_id varchar(255) for bit data not null,
  created timestamp not null,
  oauth_id varchar(255),
  username varchar(50) not null, primary key (user_id))

create unique index UK_9rvnjjykr56pfibmm9cef96rc on user_table (oauth_id)

alter table progress add constraint FKoitnd0cjj0jgwa3kpfcvwdyfy
  foreign key (user_field) references user_table

alter table progress_levels add constraint FKsdip0uy2b73ha80nk7vtagvl8
  foreign key (progress_progress_id) references progress
