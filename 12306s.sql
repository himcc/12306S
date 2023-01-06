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

-- init data

INSERT INTO `t_station` VALUES (1,'北京西站','北京');
INSERT INTO `t_station` VALUES (2,'北京南站','北京');
INSERT INTO `t_station` VALUES (3,'石家庄站','石家庄');
INSERT INTO `t_station` VALUES (4,'郑州站','郑州');
INSERT INTO `t_station` VALUES (5,'郑州东站','郑州');
INSERT INTO `t_station` VALUES (6,'武汉站','武汉');
INSERT INTO `t_station` VALUES (7,'汉口站','武汉');
INSERT INTO `t_station` VALUES (8,'武昌站','武汉');
INSERT INTO `t_station` VALUES (9,'长沙站','长沙');
INSERT INTO `t_station` VALUES (10,'长沙南站','长沙');
INSERT INTO `t_station` VALUES (11,'广州站','广州');
INSERT INTO `t_station` VALUES (12,'广州南站','广州');
INSERT INTO `t_station` VALUES (13,'西安站','西安');
INSERT INTO `t_station` VALUES (14,'西安北站','西安');
INSERT INTO `t_station` VALUES (15,'虹桥站','上海');
INSERT INTO `t_station` VALUES (16,'上海站','上海');
INSERT INTO `t_station` VALUES (17,'天津南站','天津');
INSERT INTO `t_station` VALUES (18,'济南西站','济南');
INSERT INTO `t_station` VALUES (19,'徐州东站','徐州');
INSERT INTO `t_station` VALUES (20,'南京南站','南京');
INSERT INTO `t_station` VALUES (21,'成都站','成都');
INSERT INTO `t_station` VALUES (22,'重庆站','重庆');
INSERT INTO `t_station` VALUES (23,'杭州站','杭州');
INSERT INTO `t_station` VALUES (24,'合肥站','合肥');
INSERT INTO `t_station` VALUES (25,'福州站','福州');
INSERT INTO `t_station` VALUES (26,'太原站','太原');
INSERT INTO `t_station` VALUES (27,'贵阳站','贵阳');


INSERT INTO `t_train` VALUES (15,'G120','[{\"id\":1,\"stationName\":\"北京南站\",\"arriveMin\":480,\"leaveMin\":480},{\"id\":2,\"stationName\":\"天津南站\",\"arriveMin\":510,\"leaveMin\":515},{\"id\":3,\"stationName\":\"济南西站\",\"arriveMin\":570,\"leaveMin\":575},{\"id\":4,\"stationName\":\"徐州东站\",\"arriveMin\":630,\"leaveMin\":635},{\"id\":5,\"stationName\":\"南京南站\",\"arriveMin\":690,\"leaveMin\":695},{\"id\":6,\"stationName\":\"虹桥站\",\"arriveMin\":750,\"leaveMin\":750}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (16,'G130','[{\"id\":1,\"stationName\":\"北京西站\",\"arriveMin\":420,\"leaveMin\":420},{\"id\":2,\"stationName\":\"石家庄站\",\"arriveMin\":480,\"leaveMin\":485},{\"id\":3,\"stationName\":\"郑州东站\",\"arriveMin\":540,\"leaveMin\":545},{\"id\":4,\"stationName\":\"武汉站\",\"arriveMin\":600,\"leaveMin\":605},{\"id\":5,\"stationName\":\"长沙南站\",\"arriveMin\":660,\"leaveMin\":665},{\"id\":6,\"stationName\":\"广州站\",\"arriveMin\":720,\"leaveMin\":720}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (17,'T140','[{\"id\":1,\"stationName\":\"北京西站\",\"arriveMin\":360,\"leaveMin\":360},{\"id\":2,\"stationName\":\"石家庄站\",\"arriveMin\":420,\"leaveMin\":425},{\"id\":3,\"stationName\":\"太原站\",\"arriveMin\":480,\"leaveMin\":485},{\"id\":4,\"stationName\":\"西安站\",\"arriveMin\":540,\"leaveMin\":545},{\"id\":5,\"stationName\":\"成都站\",\"arriveMin\":600,\"leaveMin\":600}]','[{\"id\":1,\"seatType\":\"硬卧\",\"seatNum\":9},{\"id\":2,\"seatType\":\"硬座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"硬座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (18,'G210','[{\"id\":1,\"stationName\":\"上海站\",\"arriveMin\":720,\"leaveMin\":720},{\"id\":2,\"stationName\":\"南京南站\",\"arriveMin\":780,\"leaveMin\":785},{\"id\":3,\"stationName\":\"徐州东站\",\"arriveMin\":900,\"leaveMin\":911},{\"id\":4,\"stationName\":\"济南西站\",\"arriveMin\":960,\"leaveMin\":971},{\"id\":5,\"stationName\":\"天津南站\",\"arriveMin\":1020,\"leaveMin\":1031},{\"id\":6,\"stationName\":\"北京南站\",\"arriveMin\":1080,\"leaveMin\":1080}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (19,'G230','[{\"id\":1,\"stationName\":\"虹桥站\",\"arriveMin\":791,\"leaveMin\":791},{\"id\":2,\"stationName\":\"杭州站\",\"arriveMin\":851,\"leaveMin\":857},{\"id\":3,\"stationName\":\"福州站\",\"arriveMin\":911,\"leaveMin\":917},{\"id\":4,\"stationName\":\"广州南站\",\"arriveMin\":971,\"leaveMin\":971}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (20,'G240','[{\"id\":1,\"stationName\":\"上海站\",\"arriveMin\":441,\"leaveMin\":441},{\"id\":2,\"stationName\":\"南京南站\",\"arriveMin\":501,\"leaveMin\":506},{\"id\":3,\"stationName\":\"合肥站\",\"arriveMin\":561,\"leaveMin\":566},{\"id\":4,\"stationName\":\"汉口站\",\"arriveMin\":621,\"leaveMin\":626},{\"id\":5,\"stationName\":\"重庆站\",\"arriveMin\":681,\"leaveMin\":686},{\"id\":6,\"stationName\":\"成都站\",\"arriveMin\":741,\"leaveMin\":741}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (21,'G310','[{\"id\":1,\"stationName\":\"广州站\",\"arriveMin\":381,\"leaveMin\":381},{\"id\":2,\"stationName\":\"长沙南站\",\"arriveMin\":501,\"leaveMin\":506},{\"id\":3,\"stationName\":\"武昌站\",\"arriveMin\":561,\"leaveMin\":566},{\"id\":4,\"stationName\":\"郑州东站\",\"arriveMin\":621,\"leaveMin\":626},{\"id\":5,\"stationName\":\"石家庄站\",\"arriveMin\":681,\"leaveMin\":686},{\"id\":6,\"stationName\":\"北京西站\",\"arriveMin\":741,\"leaveMin\":741}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (22,'G320','[{\"id\":1,\"stationName\":\"广州南站\",\"arriveMin\":980,\"leaveMin\":980},{\"id\":2,\"stationName\":\"福州站\",\"arriveMin\":1040,\"leaveMin\":1045},{\"id\":3,\"stationName\":\"杭州站\",\"arriveMin\":1100,\"leaveMin\":1105},{\"id\":4,\"stationName\":\"上海站\",\"arriveMin\":1160,\"leaveMin\":1160}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (23,'T340','[{\"id\":1,\"stationName\":\"广州站\",\"arriveMin\":671,\"leaveMin\":671},{\"id\":2,\"stationName\":\"贵阳站\",\"arriveMin\":731,\"leaveMin\":736},{\"id\":3,\"stationName\":\"重庆站\",\"arriveMin\":791,\"leaveMin\":796},{\"id\":4,\"stationName\":\"成都站\",\"arriveMin\":851,\"leaveMin\":851}]','[{\"id\":1,\"seatType\":\"硬卧\",\"seatNum\":9},{\"id\":2,\"seatType\":\"硬座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"硬座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (24,'G410','[{\"id\":1,\"stationName\":\"成都站\",\"arriveMin\":671,\"leaveMin\":671},{\"id\":2,\"stationName\":\"西安站\",\"arriveMin\":731,\"leaveMin\":736},{\"id\":3,\"stationName\":\"太原站\",\"arriveMin\":791,\"leaveMin\":796},{\"id\":4,\"stationName\":\"石家庄站\",\"arriveMin\":851,\"leaveMin\":856},{\"id\":5,\"stationName\":\"北京西站\",\"arriveMin\":911,\"leaveMin\":911}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (25,'G420','[{\"id\":1,\"stationName\":\"成都站\",\"arriveMin\":671,\"leaveMin\":671},{\"id\":2,\"stationName\":\"重庆站\",\"arriveMin\":731,\"leaveMin\":736},{\"id\":3,\"stationName\":\"武汉站\",\"arriveMin\":791,\"leaveMin\":796},{\"id\":4,\"stationName\":\"合肥站\",\"arriveMin\":851,\"leaveMin\":856},{\"id\":5,\"stationName\":\"南京南站\",\"arriveMin\":911,\"leaveMin\":916},{\"id\":6,\"stationName\":\"虹桥站\",\"arriveMin\":971,\"leaveMin\":971}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (26,'G430','[{\"id\":1,\"stationName\":\"成都站\",\"arriveMin\":84,\"leaveMin\":84},{\"id\":2,\"stationName\":\"重庆站\",\"arriveMin\":144,\"leaveMin\":150},{\"id\":3,\"stationName\":\"贵阳站\",\"arriveMin\":204,\"leaveMin\":210},{\"id\":4,\"stationName\":\"长沙南站\",\"arriveMin\":264,\"leaveMin\":264}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (27,'G510','[{\"id\":1,\"stationName\":\"郑州东站\",\"arriveMin\":451,\"leaveMin\":451},{\"id\":2,\"stationName\":\"石家庄站\",\"arriveMin\":571,\"leaveMin\":577},{\"id\":3,\"stationName\":\"北京西站\",\"arriveMin\":631,\"leaveMin\":631}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (28,'G520','[{\"id\":1,\"stationName\":\"郑州东站\",\"arriveMin\":453,\"leaveMin\":453},{\"id\":2,\"stationName\":\"徐州东站\",\"arriveMin\":513,\"leaveMin\":518},{\"id\":3,\"stationName\":\"南京南站\",\"arriveMin\":573,\"leaveMin\":578},{\"id\":4,\"stationName\":\"上海站\",\"arriveMin\":633,\"leaveMin\":633}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (29,'G530','[{\"id\":1,\"stationName\":\"郑州站\",\"arriveMin\":454,\"leaveMin\":454},{\"id\":2,\"stationName\":\"武汉站\",\"arriveMin\":514,\"leaveMin\":517},{\"id\":3,\"stationName\":\"长沙站\",\"arriveMin\":574,\"leaveMin\":577}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"一等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":4,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (30,'G540','[{\"id\":1,\"stationName\":\"郑州站\",\"arriveMin\":393,\"leaveMin\":393},{\"id\":2,\"stationName\":\"西安站\",\"arriveMin\":453,\"leaveMin\":459},{\"id\":3,\"stationName\":\"成都站\",\"arriveMin\":573,\"leaveMin\":573}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9}]');
INSERT INTO `t_train` VALUES (31,'G1520','[{\"id\":1,\"stationName\":\"北京西站\",\"arriveMin\":453,\"leaveMin\":453},{\"id\":2,\"stationName\":\"石家庄站\",\"arriveMin\":513,\"leaveMin\":518},{\"id\":3,\"stationName\":\"郑州东站\",\"arriveMin\":573,\"leaveMin\":578},{\"id\":4,\"stationName\":\"徐州东站\",\"arriveMin\":633,\"leaveMin\":638},{\"id\":5,\"stationName\":\"南京南站\",\"arriveMin\":693,\"leaveMin\":698},{\"id\":6,\"stationName\":\"上海站\",\"arriveMin\":753,\"leaveMin\":753}]','[{\"id\":1,\"seatType\":\"商务座\",\"seatNum\":9},{\"id\":2,\"seatType\":\"二等座\",\"seatNum\":9},{\"id\":3,\"seatType\":\"二等座\",\"seatNum\":9}]');




