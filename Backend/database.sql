create database wms;

create table user(
	id int auto_increment comment '主键' primary key,
    num varchar(20)	null comment '账号',
    user_name varchar(100) not null comment '名字',
    ps varchar(20) not null comment '密码',
    age int null,
    sex int null comment '性别',
    phone varchar(20) null comment '电话',
    role_id int null comment '角色 0超级管理员 1管理员 2普通用户',
    isValid varchar(4) default 'Y' null comment "有效"    
);

insert into user values(9,"han","hansummer","123456",
18,1,"18962321359",2,'Y');
