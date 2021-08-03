drop database if exists emsdb;
create database if not exists emsdb;
use emsdb;

drop table if exists login_master;
drop table if exists statusreport;
drop table if exists compliance;
drop table if exists employees;
drop table if exists department;

-- ddl queries
create table if not exists department (
    dept_id int not null auto_increment primary key,
    dept_name varchar(25) not null
);

create table if not exists compliance(
	compl_id int not null auto_increment primary key,
	rl_type varchar(15) default(null),
    details varchar(250) not null,
    create_date date not null,
    dept_id int not null,
    constraint fk_dept_id foreign key (dept_id) 
        references department(dept_id)
        on delete cascade on update cascade
);

create table if not exists employees(
	emp_id int not null auto_increment primary key,
	fname varchar(45) not null,
    lname varchar(45) not null,
    dob date not null,
    email varchar(100) default(null),
    dept_id int not null,
    constraint empfk_dept_id foreign key (dept_id) 
        references department(dept_id)
        on delete cascade on update cascade
);

create table if not exists login_master (
    userid int not null primary key,
    password varchar(32) not null,
    admin_access boolean not null,
    constraint fk_emp_id foreign key (userid)
        references employees (emp_id)
        on delete cascade on update cascade
);

create table statusreport (
    compl_id int not null,
    rpt_id int not null auto_increment primary key,
    emp_id int not null,
    comments varchar(250) not null,
    create_date date not null,
    dept_id int not null,
    constraint statusreport_ibfk_1 foreign key (dept_id)
        references department (dept_id)
        on delete cascade on update cascade,
    constraint statusreport_ibfk_2 foreign key (emp_id)
        references employees (emp_id)
        on delete cascade on update cascade,
    constraint statusreport_ibfk_3 foreign key (compl_id)
        references compliance (compl_id)
        on delete cascade on update cascade
);

-- Adding admin details -- 
insert into department values (1, "admin");
insert into employees values (1, "root", "root", "1111-1-1", "root@email.com", 1);
insert into login_master values (1, md5("root123"), 1);

