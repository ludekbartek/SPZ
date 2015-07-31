create table SUSER.Spz
(
    id int primary key not null generated always as identity,
    reqNumber varchar(10),
    priority smallint,
    issueDate timestamp,
    contactPerson varchar(32),
    requestType varchar(32),
    shortName varchar(50),
    requestDescription varchar(9000),
    implementationAcceptDate timestamp,
    ts bigint
);

create table SUSER.SpzState 
(
    id int primary key not null generated  always as identity,
    code varchar(50),
    ts bigint,
    issuer_login varchar(32),
    revisedRequestDescription varchar(9000),
    solutionDescription varchar(9000),
    assumedManDays double,
    manDays double,
    releaseNotes varchar(9000),
    classType smallint,
    idate date,
    currentState int 
);

create table SUSER.SpzStates
(
    id int primary key not null generated always as identity,
    spzId int,
    stateId int
);

create table SUSER.SpzIssuer
(
    id int primary key not null generated always as identity,
    spzId int,
    userId int
);

create table SUSER.SpzAnalyst
(
    id int primary key not null generated always as identity,
    spzId int,
    userId int
);

create table SUSER.User_
(
    id int primary key not null generated always as identity,
    login varchar(32),
    name varchar(50),
    password varchar(512),
    email varchar(50),
    company varchar(50),
    tel varchar(50),
    fax varchar(50),
    class_type smallint,
    ts bigint
);

create table SUSER.SpzNote
(
    id int primary key not null generated always as identity,
    externalNote smallint,
    noteDate timestamp,
    notetext varchar(8000),
    ts bigint
);

create table SUSER.SpzStateNote
(
    id int primary key not null generated always as identity,
    noteId int,
    stateId int
);

create table SUSER.NoteIssuer
(   
    id int primary key not null generated always as identity,
    noteId int,
    userId int
);

create table SUSER.configuration
(
    id int primary key not null generated always as identity,
    code varchar(32),
    description varchar(255),
    seqnumber bigint,
    ts bigint
);

create table SUSER.ProjectConfiguration
(
    id int primary key not null generated always as identity,
    projectId int,
    configurationId int
);

create table SUSER.UserAccess
(
    id int primary key not null generated always as identity,
    role_ varchar(32),
    ts bigint,
    userId int,
    configurationId int
);

create table SUSER.Project
(
    id int primary key not null generated always as identity,
    name varchar(32),
    description varchar(255),
    ts bigint
);

create table SUSER.Attachment
(
    id int primary key not null generated always as identity,
    date timestamp,
    content varchar(255),
    location varchar(255),
    type varchar(255),
    ts bigint
);

create table SUSER.AttachmentNote
(
    id int primary key not null generated always as identity,
    attachmentId int,
    spznoteId int
);

create table SUSER.Roles
(
    id int primary key not null generated always as identity,
    userId int,
    role_  varchar(32)
);