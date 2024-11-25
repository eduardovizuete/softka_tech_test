create table `person` (
        `personId` bigint not null auto_increment,
        `address` varchar(255) not null,
        `age` integer not null,
        `gender` varchar(255) not null,
        `identification` varchar(255) not null,
        `name` varchar(255) not null,
        `telephone` varchar(255) not null,
        primary key (`personId`)
    );

create table client (
        clientId bigint not null,
        password varchar(255) not null,
        status varchar(255) not null,
        personId bigint not null,
        primary key (personId)
    );