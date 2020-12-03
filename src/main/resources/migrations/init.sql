create schema app;
create table app.deps (
    id serial primary key,
    dep_code varchar(20),
    dep_job varchar(100),
    description varchar(255),
    unique (dep_code, dep_job)
);