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

-- test
use db_12306; show tables ;
use db_12306; desc t_user;
use db_12306; select * from t_user;