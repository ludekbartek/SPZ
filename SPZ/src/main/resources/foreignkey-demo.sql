create table test
(
    id int primary key not null generated always as identity,
    name varchar(20)
);

create table otherentity
(
    id int primary key not null generated always as identity,
    dsc varchar(20),
    testid int constraint testId references test
    
);
