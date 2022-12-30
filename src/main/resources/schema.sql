create table if not exists Cluster
(
    ClusterId     int                         default 0           not null
        primary key,
    Name          varchar(10)                                     not null comment 'Cluster name (to display)',
    Region        varchar(32) charset utf8mb4 default 'Frankfurt' not null comment 'Regional location of the cluster',
    WebMailDomain varchar(128) charset utf8mb4                    not null comment 'Default WebMail domain for cluster',
    MySQLServer   varchar(128) charset utf8mb4                    not null comment 'Default MySQL server for cluster',
    FTPServer     varchar(128) charset utf8mb4                    not null comment 'Default FTP server for cluster',
    PanelDomain   varchar(128) charset utf8mb4                    not null comment 'Default Control Panel domain for cluster',
    ShellServer   varchar(128) charset utf8mb4                    not null comment 'Default SSH server for cluster'
)
    comment 'Hosting clusters';

create table if not exists Hoster
(
    HosterId    int auto_increment
        primary key,
    Password    varchar(40) charset utf8mb4                                null,
    Company     varchar(80) charset utf8mb4                                null,
    Address1    varchar(70) charset utf8mb4                                null,
    Address2    varchar(70) charset utf8mb4                                null,
    City        varchar(40) charset utf8mb4                                null,
    State       varchar(40) charset utf8mb4                                null,
    Country     varchar(40) charset utf8mb4                                null,
    PostalCode  varchar(10) charset utf8mb4                                null,
    Phone       varchar(24) charset utf8mb4                                null,
    AltPhone    varchar(24) charset utf8mb4                                null,
    Fax         varchar(24) charset utf8mb4                                null,
    Email       varchar(60) charset utf8mb4                                not null,
    Status      enum ('New', 'OK', 'Suspend', 'Disable') default 'New'     not null,
    Type        enum ('Regular', 'Demo', 'Free')         default 'Regular' not null,
    StartDate   datetime                                                   null,
    SuspendDate datetime                                                   null,
    DNS1_Host   varchar(100) charset utf8mb4                               null,
    DNS1_IP     varchar(16) charset utf8mb4                                null,
    DNS2_Host   varchar(100) charset utf8mb4                               null,
    DNS2_IP     varchar(16) charset utf8mb4                                null,
    Mail_Host   varchar(100) charset utf8mb4                               null,
    Mail_IP     varchar(16) charset utf8mb4                                null,
    MySQL_Host  varchar(100) charset utf8mb4                               null,
    MySQL_IP    varchar(16) charset utf8mb4                                null,
    ClusterId   int                                      default 0         not null,
    constraint Hoster_Cluster_null_fk
        foreign key (ClusterId) references Cluster (ClusterId)
);

create table if not exists Product
(
    Code          varchar(32) charset utf8mb4                   not null
        primary key,
    Name          varchar(64) charset utf8mb4                   not null comment 'Product name (to display)',
    Type          enum ('Package', 'Service') default 'Package' not null,
    Billing       enum ('Monthly', 'Yearly', 'Daily')           null,
    Description   text                                          null,
    Price         float(8, 2)                 default 0.00      not null,
    Mailboxes     int unsigned                default '0'       not null comment 'Mailboxes Limit',
    Quota         int unsigned                default '0'       not null comment 'Storage Quota in Megabytes',
    TransferLimit int unsigned                default '0'       not null comment 'Outbound Web ad FTP Traffic Limit in Megabytes',
    MailQuota     int unsigned                default '1024'    not null comment 'Single Mailbox Quota in Megabytes',
    FtpUserLimit  int unsigned                default '0'       not null comment 'Maximum FTP users count'
);

create table if not exists Customer
(
    CustomerId    int auto_increment
        primary key,
    FirstName     varchar(40) charset utf8mb4                              not null,
    LastName      varchar(20) charset utf8mb4                              not null,
    Company       varchar(80) charset utf8mb4                              null,
    Address1      varchar(70) charset utf8mb4                              null,
    Address2      varchar(70) charset utf8mb4                              null,
    City          varchar(40) charset utf8mb4                              null,
    State         varchar(40) charset utf8mb4                              null,
    Country       varchar(40) charset utf8mb4                              null,
    PostalCode    varchar(10) charset utf8mb4                              null,
    Phone         varchar(24) charset utf8mb4                              null,
    AltPhone      varchar(24) charset utf8mb4                              null,
    Fax           varchar(24) charset utf8mb4                              null,
    Email         varchar(60) charset utf8mb4                              not null,
    PaymentMethod enum ('Credit', 'Free', 'PayPal') default 'Credit'       not null,
    CardType      enum ('Visa', 'Mastercard', 'American Express', 'Other') null,
    CardNumber    varchar(20) charset utf8mb4                              null,
    CardExpiry    varchar(7) charset utf8mb4                               null,
    CardHolder    varchar(30) charset utf8mb4                              null,
    HosterId      int                                                      not null,
    constraint Customer_pk
        unique (FirstName, LastName, Email),
    constraint Customer_Hoster_null_fk
        foreign key (HosterId) references Hoster (HosterId)
);

create table Domain
(
    Domain        varchar(255)                                               not null
        primary key,
    Type          enum ('Regular', 'Subdomain')            default 'Regular' not null,
    Password      varchar(255)                                               null,
    Status        enum ('New', 'OK', 'Suspend', 'Disable') default 'New'     not null,
    StartDate     datetime                                                   not null,
    SuspendDate   datetime                                                   null,
    ExpireDate    datetime                                                   null,
    ProductCode   varchar(32)                                                not null,
    CustomerId    int                                                        not null,
    PrimaryDomain varchar(255)                                               null,
    constraint Domain_Customer_null_fk
        foreign key (CustomerId) references Customer (CustomerId),
    constraint Domain_Domain_Domain_fk
        foreign key (PrimaryDomain) references Domain (Domain),
    constraint Domain_Product_Code_fk
        foreign key (ProductCode) references Product (Code)
);

create table if not exists DomainService
(
    Id          int unsigned auto_increment
        primary key,
    Status      enum ('OK', 'Suspend', 'Disable', 'Purchase') default 'OK' not null,
    Price       float(8, 2)                                   default 0.00 not null,
    Quantity    int unsigned                                  default '1'  not null comment 'Count of services',
    StartDate   datetime                                                   null,
    SuspendDate datetime                                                   null,
    ExpireDate  datetime                                                   null,
    Domain      varchar(255) charset utf8mb4                               not null,
    Product     varchar(32) charset utf8mb4                                null,
    constraint DomainService_Domain_null_fk
        foreign key (Domain) references Domain (Domain),
    constraint DomainService_Product_Code_fk
        foreign key (Product) references Product (Code)
);
