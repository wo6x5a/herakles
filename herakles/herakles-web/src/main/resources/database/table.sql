DROP TABLE IF EXISTS sys_biz_log;
CREATE TABLE sys_biz_log (
	id varchar(32) NOT NULL COMMENT '编号',
	entity varchar(40) NOT NULL COMMENT '操作实体',
	entity_id varchar(32) NOT NULL COMMENT '实体编号',
	opt_type int(1) NOT NULL COMMENT '操作类型',
	operate varchar(30) NOT NULL COMMENT '具体操作',
	opt_ip varchar(20) COMMENT '操作IP地址',
	new_value text COMMENT '新数据',
	old_value text COMMENT '旧数据',
	descr varchar(200) COMMENT '备注',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS product;
CREATE TABLE product (
	id varchar(32) NOT NULL COMMENT '编号',
	category int(3) NOT NULL COMMENT '类型',
	name varchar(80) NOT NULL COMMENT '名称',
	descr varchar(255) NOT NULL COMMENT '备注',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS gl_config;
CREATE TABLE gl_config (
	cfg_key varchar(32) NOT NULL COMMENT '参数编号',
	cfg_value varchar(40) NOT NULL COMMENT '参数值',
	cfg_type int(1) NOT NULL COMMENT '参数类型',
	descr varchar(100) NOT NULL COMMENT '参数备注',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (cfg_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS gl_dict;
CREATE TABLE gl_dict (
	id varchar(32) NOT NULL COMMENT 'id',
	category varchar(20) NOT NULL COMMENT '类型',
	dict_code varchar(100) NOT NULL COMMENT '字典编号',
	dict_text varchar(100) NOT NULL COMMENT '字典内容',
	dict_text_full varchar(200) NOT NULL COMMENT '字典内容详情',
	parent_dict_code varchar(100) NOT NULL COMMENT '父节点编号',
	enable_flg int(1) NOT NULL COMMENT '是否可用,1:是,2:否',
	leaf_flg int(1) NOT NULL COMMENT '是否子节点,1:是,2:否',
	dict_ord decimal(17,0) NOT NULL COMMENT '字典顺序',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS GL_FILE;
CREATE TABLE GL_FILE (
	file_id varchar(32) NOT NULL COMMENT '文件Id',
	file_code varchar(32) NOT NULL COMMENT '文件编码',
	file_name varchar(255) NOT NULL COMMENT '文件名',
	file_path varchar(255) NOT NULL COMMENT '文件路径',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
PRIMARY KEY (file_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS sys_perm;
CREATE TABLE sys_perm (
	perm_id varchar(32) NOT NULL COMMENT '功能权限编号',
	perm_code varchar(32) NOT NULL COMMENT '功能权限代码',
	perm_name varchar(32) NOT NULL COMMENT '功能权限名称',
	perm_descr varchar(32) COMMENT '功能权限说明',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (perm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS SYS_ROLE_PERM;
CREATE TABLE SYS_ROLE_PERM (
	role_id varchar(32) NOT NULL COMMENT '角色ID',
	perm_id varchar(32) NOT NULL COMMENT '权限ID',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (role_id,perm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
	role_id varchar(32) NOT NULL COMMENT '角色编号',
	role_code varchar(20) NOT NULL COMMENT '角色代码',
	role_name varchar(60) NOT NULL COMMENT '角色名称',
	role_descr varchar(200) COMMENT '角色备注',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (role_id),
UNIQUE KEY ROLE_CODE (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
	user_id varchar(32) NOT NULL COMMENT '用户编号',
	role_id varchar(32) NOT NULL COMMENT '角色编号',
	status varchar(1) NOT NULL DEFAULT '1' COMMENT '0-待生效, 1-已生效，默认1-已生效',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (user_id,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS sys_prsnl_ext;
CREATE TABLE sys_prsnl_ext (
	user_id varchar(32) NOT NULL COMMENT '用户编号',
	sname varchar(60) COMMENT '用户姓名',
	birth_dt varchar(10) COMMENT '出生日期',
	sex int(1) COMMENT '性别',
	id_type int(2) COMMENT '证件类型',
	id_no varchar(100) COMMENT '证件号码',
	id_img_1 varchar(100) COMMENT '证件正面图片',
	id_img_2 varchar(100) COMMENT '证件反面图片',
	id_start_dt varchar(10) COMMENT '身份证有效期开始日期yyyy-MM-dd',
	id_end_dt varchar(10) COMMENT '身份证有效期截止日期yyyy-MM-dd',
	job_type int(3) COMMENT '工作类型',
	job_desc varchar(100) COMMENT '工作简介',
	edu_type int(3) COMMENT '教育程度',
	tel varchar(20) COMMENT '联系电话',
	qq varchar(20) COMMENT 'QQ号码',
	wx varchar(40) COMMENT '微信号码',
	fax varchar(20) COMMENT '传真号码',
	zip_code varchar(20) COMMENT '邮政编码',
	addr varchar(200) COMMENT '联系地址',
	work_unit varchar(100) COMMENT '工作单位',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (user_id),
UNIQUE KEY ID_NO (id_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
	user_id varchar(32) NOT NULL COMMENT '用户编号',
	nick_name varchar(20) NOT NULL COMMENT '用户名',
	sname varchar(40) NOT NULL COMMENT '用户姓名',
	password varchar(100) NOT NULL COMMENT '密码',
	status int(2) NOT NULL COMMENT '状态:1 Active, 2 Inactive, 3 Cancelled',
	email varchar(100) COMMENT '邮箱',
	mobile varchar(20) NOT NULL COMMENT '手机号',
	user_type int(2) NOT NULL COMMENT '用户类型',
	region varchar(30) COMMENT '所属区域',
	icon_file_id varchar(100) COMMENT '用户头像',
	last_login_ts datetime(6) COMMENT '最后登录时间',
	login_fail_ct decimal(17,0) COMMENT '登录失败次数',
	last_login_fail_ts datetime(6) COMMENT '最后登陆失败时间',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS sys_user_ver_jnl;
CREATE TABLE sys_user_ver_jnl (
	jnl_no varchar(32) NOT NULL COMMENT '流水号',
	user_id varchar(32) NOT NULL COMMENT '用户编号',
	status int(1) NOT NULL COMMENT '验证码状态',
	mobile varchar(20) NOT NULL COMMENT '手机号',
	ver_code varchar(100) NOT NULL COMMENT '验证码',
	ver_end_dttm decimal(17,0) NOT NULL COMMENT '验证码失效时间戳',
	create_opid varchar(40) NOT NULL COMMENT '创建人',
	create_ts datetime(6) NOT NULL COMMENT '创建时间',
	last_mnt_opid varchar(40) COMMENT '最后修改人',
	last_mnt_ts datetime(6) COMMENT '最后修改时间',
	version_ct decimal(17,0) DEFAULT '0' COMMENT '版本号',
PRIMARY KEY (jnl_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
