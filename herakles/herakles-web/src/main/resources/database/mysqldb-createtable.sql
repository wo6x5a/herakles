-- table structure for table `sys_user`
drop table if exists `sys_user`;
create table `sys_user` (
  `user_id` varchar(32) not null comment '用户编号',
  `nick_name` varchar(20) not null comment '用户名',
  `sname` varchar(60) not null comment '用户姓名',
  `password` varchar(100) not null comment '登陆密码',
  `status` int(2) not null comment '状态',
  `mobile` varchar(20) not null comment '手机号码',
  `email` varchar(100) default null comment '邮箱',
  `user_type` int(2) not null comment '用户类型(1-个人，2-企业)',
  `region` varchar(30) default null comment '所属区域(待定)',
  `icon_file_id` varchar(100) default null comment '用户头像(待定)',
  `last_login_ts` datetime(6) default null comment '最后登陆时间',
  `login_fail_ct` int(11) default null comment '登陆失败次数',
  `last_login_fail_ts` datetime(6) default null comment '最后登陆失败时间',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime(6) not null comment '创建时间',
  `last_mnt_opid` varchar(32) default null comment '最后修改人',
  `last_mnt_ts` datetime(6) default null comment '最后修改时间',
  `version_ct` bigint(20) not null comment '版本号',
  primary key (`user_id`),
  unique key `uk_ths_sys_user_nick_name` (`nick_name`),
  unique key `uk_ths_sys_user_mobile` (`mobile`),
  unique key `uk_ths_sys_user_email` (`email`) using btree
) engine=innodb default charset=utf8 comment='系统操作用户';

-- table structure for table `gl_dict`
drop table if exists `gl_dict`;
create table `gl_dict` (
  `id` varchar(32) not null comment '编号',
  `category` varchar(20) default null comment '大类',
  `dict_code` varchar(100) default null comment '字典编号',
  `dict_text` varchar(100) default null comment '名称',
  `dict_text_full` varchar(200) default null comment '全名称',
  `parent_dict_code` varchar(100) default null comment '父字典编号',
  `enable_flg` int(1) default null comment '是否可用(1-是；2-否; 3-未知)',
  `leaf_flg` int(1) default null comment '是否叶子节点(1-是；2-否; 3-未知)',
  `dict_ord` int(11) default null comment '顺序编号',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime not null comment '创建时间',
  `version_ct` bigint(20) not null comment '版本号',
  primary key (`id`)
) engine=innodb default charset=utf8 comment='数据字典表';

-- table structure for table `gl_file`
drop table if exists `gl_file`;
create table `gl_file` (
  `id` varchar(32) not null comment '文件id',
  `file_code` varchar(32) default null comment '文件编码',
  `file_name` varchar(255) default null comment '文件名',
  `file_path` varchar(255) default null comment '文件路径',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime not null comment '创建时间',
  primary key (`id`)
) engine=innodb default charset=utf8 comment='文件表';

-- table structure for table `sys_biz_log`
drop table if exists `sys_biz_log`;
create table `sys_biz_log` (
  `id` varchar(64) not null comment '编号',
  `type` varchar(20) comment '日志类型',
  `title` varchar(255) default '' comment '日志标题',
  `remote_addr` varchar(255) default null comment '操作ip地址',
  `request_uri` varchar(255) default null comment '请求uri',
  `params` text comment '操作提交的数据',
  `exception` text comment '异常信息',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime not null comment '创建时间',
  primary key (`id`),
  key `sys_biz_log_create_opid` (`create_opid`),
  key `sys_biz_log_request_uri` (`request_uri`),
  key `sys_biz_log_type` (`type`),
  key `sys_biz_log_create_ts` (`create_ts`)
) engine=innodb default charset=utf8 comment='系统业务日志表';


-- table structure for table `product`
drop table if exists `product`;
create table `product` (
  `id` varchar(32) not null comment '编号',
  `category` int(3) not null comment '类别',
  `name` varchar(80) default null comment '名称',
  `description` varchar(255) default null comment '描述',
  `create_opid` varchar(32) default null comment '创建人',
  `create_ts` timestamp not null comment '创建时间',
  `last_mnt_opid` varchar(32) default null comment '最后修改人',
  `last_mnt_ts` datetime(6) default null comment '最后修改时间',
  `version_ct` bigint(20) default null comment '版本号',
  primary key (`id`)
) engine=innodb default charset=utf8 comment='测试用例表';

-- table structure for table `sys_perm`
drop table if exists `sys_perm`;
create table `sys_perm` (
  `perm_id` varchar(32) not null comment '功能权限编号',
  `perm_name` varchar(25) not null comment '功能权限名称',
  `perm_code` varchar(25) not null comment '功能权限代码',
  `perm_desc` varchar(80) default null comment '备注',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime(6) not null comment '创建时间',
  `last_mnt_opid` varchar(32) default null comment '最后修改人',
  `last_mnt_ts` datetime(6) default null comment '最后修改时间',
  `version_ct` bigint(20) not null comment '版本号',
  primary key (`perm_id`)
) engine=innodb default charset=utf8 comment='功能权限表';

-- table structure for table `sys_role`
drop table if exists `sys_role`;
create table `sys_role` (
  `role_id` varchar(32) not null comment '角色编号',
  `role_code` varchar(25) not null comment '角色代码',
  `role_name` varchar(25) not null comment '角色名称',
  `role_desc` varchar(80) default null comment '备注',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime(6) not null comment '创建时间',
  `last_mnt_opid` varchar(32) default null comment '最后修改人',
  `last_mnt_ts` datetime(6) default null comment '最后修改时间',
  `version_ct` bigint(20) not null comment '版本号',
  primary key (`role_id`)
) engine=innodb default charset=utf8 comment='角色表';

-- table structure for table `sys_role_perm`
drop table if exists `sys_role_perm`;
create table `sys_role_perm` (
  `role_id` varchar(32) not null comment '权限编号',
  `perm_id` varchar(32) not null comment '功能编号',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime(6) not null comment '创建时间',
  `last_mnt_opid` varchar(32) default null comment '最后修改人',
  `last_mnt_ts` datetime(6) default null comment '最后修改时间',
  `version_ct` bigint(20) not null comment '版本号',
  primary key (`role_id`,`perm_id`),
  key `fk_role_perm_2` (`perm_id`),
  constraint `fk_role_perm_1` foreign key (`role_id`) references `sys_role` (`role_id`),
  constraint `fk_role_perm_2` foreign key (`perm_id`) references `sys_perm` (`perm_id`)
) engine=innodb default charset=utf8 comment='角色和功能权限关联表';

-- table structure for table `sys_user_role`
drop table if exists `sys_user_role`;
create table `sys_user_role` (
  `user_id` varchar(32) not null comment '用户编号',
  `role_id` varchar(32) not null comment '角色编号',
  `status` varchar(10) not null comment '状态',
  `create_opid` varchar(32) not null comment '创建人',
  `create_ts` datetime(6) not null comment '创建时间',
  `last_mnt_opid` varchar(32) default null comment '最后修改人',
  `last_mnt_ts` datetime(6) default null comment '最后修改时间',
  `version_ct` bigint(20) not null comment '版本号',
  primary key (`user_id`,`role_id`),
  key `fk_user_role_2` (`role_id`),
  constraint `fk_user_role_1` foreign key (`user_id`) references `sys_user` (`user_id`),
  constraint `fk_user_role_2` foreign key (`role_id`) references `sys_role` (`role_id`)
) engine=innodb default charset=utf8 comment='用户和角色关联表';
