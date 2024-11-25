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

create table `client` (
        `clientId` bigint not null,
        `password` varchar(255) not null,
        `status` varchar(255) not null,
        `personId` bigint not null,
        primary key (personId)
    );

create table `account` (
        `id` bigint not null auto_increment,
        `balance` float(53) not null,
        `number` varchar(255) not null,
        `status` varchar(255) not null,
        `type` varchar(255) not null,
        `client_personId` bigint not null,
        primary key (id)
    );

 create table `transaction` (
        `id` bigint not null auto_increment,
        `amount` float(53),
        `balance` float(53),
        `balanceBeforeTx` float(53),
        `date` datetime(6),
        `type` enum ('DEPOSIT','WITHDRAWAL'),
        `account_id` bigint not null,
        primary key (id)
    );