SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `ie_account`;
CREATE TABLE `ie_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='账户表';


DROP TABLE IF EXISTS `ie_dept`;
CREATE TABLE `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` int(11) DEFAULT NULL COMMENT '父部门id',
  `name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='部门表';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 一个用户多个账号
DROP TABLE IF EXISTS `ie_user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `idtype` varchar(45) DEFAULT NULL COMMENT '证件类型',
  `idno` varchar(45) DEFAULT NULL COMMENT '证件号',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `mobile` varchar(45) DEFAULT NULL COMMENT '手机号',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `deptid` int(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='管理员表';


-- 一个用户多个账号

DROP TABLE IF EXISTS `ie_sysdic`;
CREATE TABLE `ie_sysdic` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `pid` varchar(32) DEFAULT NULL COMMENT '父级id',
  `code` varchar(100) DEFAULT NULL COMMENT '字典编码',
  `name` varchar(100) DEFAULT NULL COMMENT '字典名称',
  `ctype` varchar(32) DEFAULT NULL COMMENT '字典类型',
  `sortindex` int(11) DEFAULT NULL COMMENT '排序',
  
  `TITLE` varchar(100) DEFAULT NULL COMMENT '标题',
  `ORGI` varchar(32) DEFAULT NULL COMMENT '租户ID',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `MEMO` varchar(32) DEFAULT NULL COMMENT '备注',
  `ICONSTR` varchar(255) DEFAULT NULL COMMENT '图标',
  `ICONSKIN` varchar(255) DEFAULT NULL COMMENT '自定义样式',
  `CATETYPE` varchar(32) DEFAULT NULL COMMENT '类型',
  `CREATER` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  `HASCHILD` tinyint(4) DEFAULT NULL COMMENT '是否有下级',
  `DICID` varchar(32) DEFAULT NULL COMMENT '目录ID',
  `DEFAULTVALUE` tinyint(4) DEFAULT NULL COMMENT '默认值',
  `DISCODE` tinyint(4) DEFAULT NULL COMMENT '编码',
  `URL` varchar(255) DEFAULT NULL COMMENT '系统权限资源的URL',
  `MODULE` varchar(32) DEFAULT NULL COMMENT '权限资源所属模块',
  `MLEVEL` varchar(32) DEFAULT NULL COMMENT '菜单级别（一级/二级）',
  `RULES` varchar(100) DEFAULT NULL,
  `MENUTYPE` varchar(32) DEFAULT NULL COMMENT '菜单类型（顶部菜单/左侧菜单）',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SQL121227155532210` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
