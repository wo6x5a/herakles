
-- TABLE STRUCTURE FOR TABLE `SYS_USER`
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `USER_ID` VARCHAR(32) NOT NULL COMMENT '用户编号',
  `NICK_NAME` VARCHAR(20) NOT NULL COMMENT '用户名',
  `SNAME` VARCHAR(60) NOT NULL COMMENT '用户姓名',
  `PASSWORD` VARCHAR(100) NOT NULL COMMENT '登陆密码',
  `STATUS` VARCHAR(10) NOT NULL COMMENT '状态',
  `MOBILE` VARCHAR(20) NOT NULL COMMENT '手机号码',
  `EMAIL` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `USER_TYPE` VARCHAR(1) NOT NULL COMMENT '用户类型(P-个人，E-企业，IO-投资机构,SO-服务机构)',
  `REGION` VARCHAR(30) DEFAULT NULL COMMENT '所属区域(待定)',
  `ICON_FILE_ID` VARCHAR(100) DEFAULT NULL COMMENT '用户头像(待定)',
  `LAST_LOGIN_TS` DATETIME(6) DEFAULT NULL COMMENT '最后登陆时间',
  `LOGIN_FAIL_CT` INT(11) DEFAULT NULL COMMENT '登陆失败次数',
  `LAST_LOGIN_FAIL_TS` DATETIME(6) DEFAULT NULL COMMENT '最后登陆失败时间',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` VARCHAR(32) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` DATETIME(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` BIGINT(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UK_THS_SYS_USER_NICK_NAME` (`NICK_NAME`),
  UNIQUE KEY `UK_THS_SYS_USER_MOBILE` (`MOBILE`),
  UNIQUE KEY `UK_THS_SYS_USER_EMAIL` (`EMAIL`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='系统操作用户';

-- TABLE STRUCTURE FOR TABLE `GL_DICT`
DROP TABLE IF EXISTS `GL_DICT`;
CREATE TABLE `GL_DICT` (
  `ID` VARCHAR(32) NOT NULL COMMENT '编号',
  `CATEGORY` VARCHAR(20) DEFAULT NULL COMMENT '大类',
  `DICT_CODE` VARCHAR(100) DEFAULT NULL COMMENT '字典编号',
  `DICT_TEXT` VARCHAR(100) DEFAULT NULL COMMENT '名称',
  `DICT_TEXT_FULL` VARCHAR(200) DEFAULT NULL COMMENT '全名称',
  `PARENT_DICT_CODE` VARCHAR(100) DEFAULT NULL COMMENT '父字典编号',
  `ENABLE_FLG` INT(11) DEFAULT NULL COMMENT '是否可用(0-否；1-是)',
  `LEAF_FLG` INT(11) DEFAULT NULL COMMENT '是否叶子节点',
  `DICT_ORD` INT(11) DEFAULT NULL COMMENT '顺序编号',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME NOT NULL COMMENT '创建时间',
  `VERSION_CT` BIGINT(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='数据字典表';


-- TABLE STRUCTURE FOR TABLE `GL_FILE`
DROP TABLE IF EXISTS `GL_FILE`;
CREATE TABLE `GL_FILE` (
  `ID` VARCHAR(32) NOT NULL COMMENT '文件Id',
  `FILE_CODE` VARCHAR(32) DEFAULT NULL COMMENT '文件编码',
  `FILE_NAME` VARCHAR(255) DEFAULT NULL COMMENT '文件名',
  `FILE_PATH` VARCHAR(255) DEFAULT NULL COMMENT '文件路径',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='文件表';


/*TABLE STRUCTURE FOR TABLE `SYS_LOG` */

DROP TABLE IF EXISTS `SYS_LOG`;

CREATE TABLE `SYS_LOG` (
  `ID` VARCHAR(64) NOT NULL COMMENT '编号',
  `TYPE` VARCHAR(20) COMMENT '日志类型',
  `TITLE` VARCHAR(255) DEFAULT '' COMMENT '日志标题',
  `REMOTE_ADDR` VARCHAR(255) DEFAULT NULL COMMENT '操作IP地址',
  `REQUEST_URI` VARCHAR(255) DEFAULT NULL COMMENT '请求URI',
  `PARAMS` TEXT COMMENT '操作提交的数据',
  `EXCEPTION` TEXT COMMENT '异常信息',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `SYS_LOG_CREATE_OPID` (`CREATE_OPID`),
  KEY `SYS_LOG_REQUEST_URI` (`REQUEST_URI`),
  KEY `SYS_LOG_TYPE` (`TYPE`),
  KEY `SYS_LOG_CREATE_TS` (`CREATE_TS`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE=UTF8_BIN COMMENT='日志表';

/*DATA FOR THE TABLE `SYS_LOG` */


-- TABLE STRUCTURE FOR TABLE `PRODUCT`
DROP TABLE IF EXISTS `PRODUCT`;
CREATE TABLE `PRODUCT` (
  `ID` VARCHAR(32) NOT NULL COMMENT '编号',
  `CATEGORY` VARCHAR(10) NOT NULL COMMENT '类别',
  `NAME` VARCHAR(80) DEFAULT NULL COMMENT '名称',
  `DESCRIPTION` VARCHAR(255) DEFAULT NULL COMMENT '描述',
  `CREATE_OPID` VARCHAR(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TS` TIMESTAMP NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` VARCHAR(32) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` DATETIME(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` BIGINT(20) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='测试用例表';

-- TABLE STRUCTURE FOR TABLE `SYS_PERM`
DROP TABLE IF EXISTS `SYS_PERM`;
CREATE TABLE `SYS_PERM` (
  `PERM_ID` VARCHAR(32) NOT NULL COMMENT '功能权限编号',
  `PERM_NAME` VARCHAR(25) NOT NULL COMMENT '功能权限名称',
  `PERM_CODE` VARCHAR(25) NOT NULL COMMENT '功能权限代码',
  `PERM_DESC` VARCHAR(80) DEFAULT NULL COMMENT '备注',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` VARCHAR(32) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` DATETIME(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` BIGINT(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`PERM_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='功能权限表';

-- TABLE STRUCTURE FOR TABLE `SYS_ROLE`
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
  `ROLE_ID` VARCHAR(32) NOT NULL COMMENT '角色编号',
  `ROLE_CODE` VARCHAR(25) NOT NULL COMMENT '角色代码',
  `ROLE_NAME` VARCHAR(25) NOT NULL COMMENT '角色名称',
  `ROLE_DESC` VARCHAR(80) DEFAULT NULL COMMENT '备注',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` VARCHAR(32) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` DATETIME(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` BIGINT(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='角色表';

-- TABLE STRUCTURE FOR TABLE `SYS_ROLE_PERM`
DROP TABLE IF EXISTS `SYS_ROLE_PERM`;
CREATE TABLE `SYS_ROLE_PERM` (
  `ROLE_ID` VARCHAR(32) NOT NULL COMMENT '权限编号',
  `PERM_ID` VARCHAR(32) NOT NULL COMMENT '功能编号',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` VARCHAR(32) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` DATETIME(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` BIGINT(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`ROLE_ID`,`PERM_ID`),
  KEY `FK_ROLE_PERM_2` (`PERM_ID`),
  CONSTRAINT `FK_ROLE_PERM_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `SYS_ROLE` (`ROLE_ID`),
  CONSTRAINT `FK_ROLE_PERM_2` FOREIGN KEY (`PERM_ID`) REFERENCES `SYS_PERM` (`PERM_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='角色和功能权限关联表';

-- TABLE STRUCTURE FOR TABLE `SYS_USER_ROLE`
DROP TABLE IF EXISTS `SYS_USER_ROLE`;
CREATE TABLE `SYS_USER_ROLE` (
  `USER_ID` VARCHAR(32) NOT NULL COMMENT '用户编号',
  `ROLE_ID` VARCHAR(32) NOT NULL COMMENT '角色编号',
  `STATUS` VARCHAR(10) NOT NULL COMMENT '状态',
  `CREATE_OPID` VARCHAR(32) NOT NULL COMMENT '创建人',
  `CREATE_TS` DATETIME(6) NOT NULL COMMENT '创建时间',
  `LAST_MNT_OPID` VARCHAR(32) DEFAULT NULL COMMENT '最后修改人',
  `LAST_MNT_TS` DATETIME(6) DEFAULT NULL COMMENT '最后修改时间',
  `VERSION_CT` BIGINT(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_USER_ROLE_2` (`ROLE_ID`),
  CONSTRAINT `FK_USER_ROLE_1` FOREIGN KEY (`USER_ID`) REFERENCES `SYS_USER` (`USER_ID`),
  CONSTRAINT `FK_USER_ROLE_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `SYS_ROLE` (`ROLE_ID`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='用户和角色关联表';
