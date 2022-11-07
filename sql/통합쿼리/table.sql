drop database testdb;
create database testdb;
use testdb;

# 조직 도메인
CREATE TABLE `company`
(
    `company_no`      bigint       NOT NULL auto_increment primary key,
    `orga_no`         bigint       NOT NULL,
    `company_name`    VARCHAR(200) NOT NULL,
    `company_address` VARCHAR(200) NOT NULL,
    `company_tel`     VARCHAR(100) NULL,
    `owner_name`      VARCHAR(100) NOT NULL,
    `founding_date`   date         NULL,
    `business_number` VARCHAR(100) NOT NULL,
    `company_use`     boolean      NOT NULL default true
);

CREATE TABLE `tbl_dept`
(
    `dept_no`    bigint        NOT NULL auto_increment primary key,
    `company_no` bigint        NOT NULL,
    `dname`      VARCHAR(200)  NOT NULL,
    `del_flag`   boolean       NOT NULL default false,
    `main_flag`  boolean       NOT NULL default true,
    `dept_sort`  int(11)       NOT NULL,
    `ddesc`      VARCHAR(1000) NULL,
    `regdate`    timestamp     default now(),
    `updatedate` timestamp     default now()
);

CREATE TABLE `emp`
(
    `emp_no`          bigint       NOT NULL auto_increment primary key,
    `emp_name`        VARCHAR(200) NOT NULL,
    `username`        VARCHAR(200) NOT NULL,
    `password`        VARCHAR(200) NOT NULL,
    `gender`          VARCHAR(10)  NOT NULL,
    `emp_phone`       VARCHAR(200) NULL,
    `emp_address`     VARCHAR(200) NULL,
    `emp_profile`     VARCHAR(200) NULL,
    `email`           VARCHAR(100) NULL,
    `last_login_time` timestamp    NULL,
    `last_login_ip`   VARCHAR(100) NULL,
    `first_hiredate`  date         NOT NULL,
    `last_retiredate` date         NULL
);

CREATE TABLE `orga`
(
    `orga_no`       bigint NOT NULL auto_increment primary key,
    `upper_orga_no` bigint NULL,
    `orga_type` varchar(100)
);

CREATE TABLE `empMapping`
(
    `orga_no`     bigint       NOT NULL,
    `emp_no`      bigint       NOT NULL,
    `emp_rank`    varchar(200) NOT NULL,
    `hiredate`    date         NOT NULL,
    `retireddate` date         NULL
);

CREATE TABLE `deptMapping`
(
    `dept_no` bigint NOT NULL,
    `orga_no` bigint NOT NULL
);


# 메뉴 도메인
CREATE TABLE `menu`
(
    `menu_no`           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `upper_menu_no`     BIGINT       NULL,
    `menu_code`         VARCHAR(200) NOT NULL UNIQUE,
    `default_menu_code` VARCHAR(200) NULL,
    `menu_name`         VARCHAR(200) NOT NULL,
    `menu_use`          boolean      NOT NULL DEFAULT TRUE,
    `menu_sort`         int(11)      NOT NULL DEFAULT '99',
    `icon_url`          VARCHAR(200) NOT NULL DEFAULT '#'
);

# 권한그룹
CREATE TABLE `role_group`
(
    `role_group_no`   bigint       NOT NULL auto_increment primary key,
    `role_group_name` VARCHAR(200) NOT NULL,
    `role_group_use`  boolean      NOT NULL,
    `company_name`    VARCHAR(200) NOT NULL
);

# 메뉴권한
CREATE TABLE `menu_role`
(
    `role_group_no` bigint NOT NULL,
    `menu_no`       bigint NOT NULL
);


CREATE TABLE `orga_role`
(
    `orga_role_no`  bigint NOT NULL auto_increment primary key,
    `role_group_no` bigint NOT NULL,
    `orga_no`       bigint NOT NULL
);

CREATE TABLE `mygroup`
(
    `mygroup_no` bigint NOT NULL auto_increment primary key,
    `emp_no`     bigint NOT NULL
);

CREATE TABLE `favorite`
(
    `favorite_no`     bigint NOT NULL auto_increment primary key,
    `mygroup_no`      bigint NOT NULL,
    `favorite_emp_no` bigint NOT NULL
);

# 외래기 제약조건
ALTER TABLE `company`
    ADD CONSTRAINT `FK_orga_TO_company_1` FOREIGN KEY (
                                                       `orga_no`
        )
        REFERENCES `orga` (
                           `orga_no`
            );

ALTER TABLE `tbl_dept`
    ADD CONSTRAINT `FK_company_TO_tbl_dept_1` FOREIGN KEY (
                                                       `company_no`
        )
        REFERENCES `company` (
                           `company_no`
            );

ALTER TABLE `menu`
    ADD CONSTRAINT `FK_menu_TO_menu_1` FOREIGN KEY (
                                                    `upper_menu_no`
        )
        REFERENCES `menu` (
                           `menu_no`
            );

ALTER TABLE `orga`
    ADD CONSTRAINT `FK_orga_TO_orga_1` FOREIGN KEY (
                                                    `upper_orga_no`
        )
        REFERENCES `orga` (
                           `orga_no`
            );

ALTER TABLE `orga_role`
    ADD CONSTRAINT `FK_role_group_TO_orga_role_1` FOREIGN KEY (
                                                               `role_group_no`
        )
        REFERENCES `role_group` (
                                 `role_group_no`
            );

ALTER TABLE `orga_role`
    ADD CONSTRAINT `FK_orga_TO_orga_role_1` FOREIGN KEY (
                                                         `orga_no`
        )
        REFERENCES `orga` (
                           `orga_no`
            );

ALTER TABLE `menu_role`
    ADD CONSTRAINT `FK_role_group_TO_menu_role_1` FOREIGN KEY (
                                                               `role_group_no`
        )
        REFERENCES `role_group` (
                                 `role_group_no`
            );

ALTER TABLE `menu_role`
    ADD CONSTRAINT `FK_menu_TO_menu_role_1` FOREIGN KEY (
                                                         `menu_no`
        )
        REFERENCES `menu` (
                           `menu_no`
            );

ALTER TABLE `favorite`
    ADD CONSTRAINT `FK_mygroup_TO_favorite_1` FOREIGN KEY (
                                                           `mygroup_no`
        )
        REFERENCES `mygroup` (
                              `mygroup_no`
            );

ALTER TABLE `favorite`
    ADD CONSTRAINT `FK_emp_TO_favorite_1` FOREIGN KEY (
                                                       `favorite_emp_no`
        )
        REFERENCES `emp` (
                          `emp_no`
            );

ALTER TABLE `mygroup`
    ADD CONSTRAINT `FK_emp_TO_mygroup_1` FOREIGN KEY (
                                                      `emp_no`
        )
        REFERENCES `emp` (
                          `emp_no`
            );

ALTER TABLE `empMapping`
    ADD CONSTRAINT `FK_orga_TO_empMapping_1` FOREIGN KEY (
                                                          `orga_no`
        )
        REFERENCES `orga` (
                           `orga_no`
            );

ALTER TABLE `empMapping`
    ADD CONSTRAINT `FK_emp_TO_empMapping_1` FOREIGN KEY (
                                                         `emp_no`
        )
        REFERENCES `emp` (
                          `emp_no`
            );

ALTER TABLE `deptMapping`
    ADD CONSTRAINT `FK_tbl_dept_TO_deptMapping_1` FOREIGN KEY (
                                                               `dept_no`
        )
        REFERENCES `tbl_dept` (
                               `dept_no`
            );

ALTER TABLE `deptMapping`
    ADD CONSTRAINT `FK_orga_TO_deptMapping_1` FOREIGN KEY (
                                                           `orga_no`
        )
        REFERENCES `orga` (
                           `orga_no`
            );

