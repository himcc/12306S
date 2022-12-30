CREATE DATABASE IF NOT EXISTS db_12306;

use db_12306;

-- t_user
DROP TABLE IF EXISTS t_user;

create table t_user (
    id  bigint NOT NULL auto_increment,
    username varchar(20) NOT NULL ,
    passwd varchar(100) NOT NULL,
    sys_role tinyint default 1 NOT NULL comment '0:admin , 1: user',
    PRIMARY KEY ( id ),
    unique key name_uniq (username)
) engine=innodb,charset=utf8mb4,comment='user table';

insert into t_user (username, passwd, sys_role) values ('root','$2a$10$oJ5SgABZS6HzHJmtqDLpF.bA7Pl66K9XxbNrxzdMoQM4TSsCcDtHG',0);
insert into t_user (username, passwd, sys_role) values ('user1','$2a$10$xYHkg33dp3v7Vj96YJ99T.La8kE7z/DSv/x03PpZbn48J9k64UucS',1);
insert into t_user (username, passwd, sys_role) values ('user2','$2a$10$VTHRdUYqGJ3kS/Vy7zzAVuH7xEEat6Xb7R0I4du0eptchesHuyivy',1);

-- t_station
DROP TABLE IF EXISTS t_station;
create table t_station (
    id  int NOT NULL auto_increment,
    station_name varchar(10) NOT NULL ,
    city_name varchar(10) NOT NULL,
    PRIMARY KEY ( id ),
    unique key name_uniq (station_name)
) engine=innodb,charset=utf8mb4,comment='station table';

insert into t_station (station_name,city_name) values ('北京西站','北京');
insert into t_station (station_name,city_name) values ('北京南站','北京');
insert into t_station (station_name,city_name) values ('石家庄站','石家庄');
insert into t_station (station_name,city_name) values ('郑州站','郑州');
insert into t_station (station_name,city_name) values ('郑州东站','郑州');

-- t_train
DROP TABLE IF EXISTS t_train;
create table t_train (
    id  int NOT NULL auto_increment,
    train_name varchar(10) NOT NULL ,
    station_json text NOT NULL comment '[{id,stationName,arriveMin,leaveMin}]',
    car_json text NOT NULL comment '[{id,seatType,seatNum}]  seatType:商务座、一等座、二等座、硬卧、软卧、硬座、无座',
    PRIMARY KEY ( id ),
    unique key name_uniq (train_name)
) engine=innodb,charset=utf8mb4,comment='train table';

insert into t_train (train_name,station_json,car_json) values ('G100','[{"id":1,"stationName":"北京西站","arriveMin":0,"leaveMin":0},{"id":2,"stationName":"石家庄站","arriveMin":90,"leaveMin":95},{"id":3,"stationName":"郑州东站","arriveMin":150,"leaveMin":150}]','[{"id":1,"seatType":"一等座","seatNum":80},{"id":2,"seatType":"二等座","seatNum":120}]');
insert into t_train (train_name,station_json,car_json) values ('G101','[{"id":1,"stationName":"北京西站","arriveMin":0,"leaveMin":0},{"id":2,"stationName":"石家庄站","arriveMin":90,"leaveMin":95},{"id":3,"stationName":"郑州东站","arriveMin":150,"leaveMin":150}]','[{"id":1,"seatType":"一等座","seatNum":80},{"id":2,"seatType":"二等座","seatNum":120}]');


-- t_exe_train
DROP TABLE IF EXISTS t_exe_train;
create table t_exe_train (
    id  int NOT NULL auto_increment,
    train_name varchar(10) NOT NULL ,
    exe_date date NOT NULL ,
    sale_ts bigint NOT NULL ,
    station_json text NOT NULL comment '[{id,stationName,arriveMin,leaveMin}]',
    car_json text NOT NULL comment '[{id,seatType,seatNum}]  seatType:商务座、一等座、二等座、硬卧、软卧、硬座、无座',
    PRIMARY KEY ( id ),
    unique key name_uniq (train_name,exe_date)
) engine=innodb,charset=utf8mb4,comment='exe train table';

DROP TABLE IF EXISTS t_exe_train_station;
create table t_exe_train_station (
    id  int NOT NULL auto_increment,
    exe_train_id int not null,
    train_name varchar(10) NOT NULL ,
    station_no int not null,
    station_name varchar(10) NOT NULL ,
    city_name varchar(10) NOT NULL ,
    sale_ts bigint not null,
    arrive_ts  bigint not null,
    leave_ts bigint not null,

    PRIMARY KEY ( id ),
    unique key name_uniq (exe_train_id,station_no)
) engine=innodb,charset=utf8mb4,comment='exe train station table';



DROP TABLE IF EXISTS t_seat;
create table t_seat (
    id  bigint NOT NULL auto_increment,
    exe_train_id int not null,
    car_no tinyint unsigned not null,
    seat_type tinyint unsigned NOT NULL ,
    seat_no tinyint unsigned not null,

    seg1 tinyint default 0 NOT NULL ,
    seg2 tinyint default 0 NOT NULL ,
    seg3 tinyint default 0 NOT NULL ,
    seg4 tinyint default 0 NOT NULL ,
    seg5 tinyint default 0 NOT NULL ,
    seg6 tinyint default 0 NOT NULL ,
    seg7 tinyint default 0 NOT NULL ,
    seg8 tinyint default 0 NOT NULL ,
    seg9 tinyint default 0 NOT NULL ,
    seg10 tinyint default 0 NOT NULL ,
    seg11 tinyint default 0 NOT NULL ,
    seg12 tinyint default 0 NOT NULL ,
    seg13 tinyint default 0 NOT NULL ,
    seg14 tinyint default 0 NOT NULL ,
    seg15 tinyint default 0 NOT NULL ,
    seg16 tinyint default 0 NOT NULL ,
    seg17 tinyint default 0 NOT NULL ,
    seg18 tinyint default 0 NOT NULL ,
    seg19 tinyint default 0 NOT NULL ,
    seg20 tinyint default 0 NOT NULL ,

    PRIMARY KEY ( id ),
    unique key name_uniq (exe_train_id,car_no,seat_no),
    index for_query(exe_train_id,seat_type)
) engine=innodb,charset=utf8mb4,comment='seat table';



DROP TABLE IF EXISTS t_order;
create table t_order (
    id  bigint NOT NULL auto_increment,
    uid bigint not null,

    train_name varchar(10) NOT NULL ,
    exe_train_id int not null,

    arrive_ts  bigint not null,
    leave_ts bigint not null,

    car_no tinyint unsigned not null,
    seat_type tinyint unsigned NOT NULL ,
    seat_no tinyint unsigned not null,

    seat_id bigint not null,

    leave_station_no int NOT NULL ,
    arrive_station_no int NOT NULL ,

    leave_station_name varchar(10) NOT NULL ,
    arrive_station_name varchar(10) NOT NULL ,

    price int not null,

    order_status tinyint default 0 not null comment '0 下单，1 已支付，2 退票，3 支付超时自动取消',

    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY ( id ),
    index uniq_idx (uid,create_time)
) engine=innodb,charset=utf8mb4,comment='order table';



