SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------

-- Table structure for `IE_USER`

-- ----------------------------

DROP TABLE IF EXISTS `IE_USER`;
CREATE TABLE `IE_USER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '登录名',
  `password` varchar(255) NOT NULL COMMENT '密码(加密)',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_count` int(11) NOT NULL COMMENT '登录总次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable` bit(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
  INDEX INDEX_USERNAME (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';


-- ----------------------------

-- Table structure for `IE_APP`

-- ----------------------------

DROP TABLE IF EXISTS `IE_APP`;
CREATE TABLE `IE_APP` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) NOT NULL COMMENT '编码',
  `name` varchar(128) NOT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `sort` int(11) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable` bit(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='应用表';

-- ----------------------------

-- Table structure for `IE_ROLE`

-- ----------------------------

DROP TABLE IF EXISTS `IE_ROLE`;
CREATE TABLE `IE_ROLE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  `name` varchar(128) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `sort` int(11) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable` bit(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='应用角色表';

-- ----------------------------

-- Table structure for `IE_PERMISSION`

-- ----------------------------

DROP TABLE IF EXISTS `IE_PERMISSION`;
CREATE TABLE `IE_PERMISSION` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '父ID',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `url` varchar(255) NOT NULL COMMENT '权限URL',
  `sort` int(11) NOT NULL COMMENT '排序',
  `menu` bit(1) NOT NULL COMMENT '是否菜单',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `enable` bit(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='应用权限表';


-- ----------------------------

-- Table structure for `IE_RE_ROLE_PERMISSION`

-- ----------------------------

DROP TABLE IF EXISTS `IE_RE_ROLE_PERMISSION`;
CREATE TABLE `IE_RE_ROLE_PERMISSION` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
  INDEX INDEX_APP_ID (`app_id`)
  INDEX INDEX_ROLE_ID (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------

-- Table structure for `IE_RE_USER_APP`

-- ----------------------------

DROP TABLE IF EXISTS `IE_RE_USER_APP`;
CREATE TABLE `IE_RE_USER_APP` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID ',
  `app_id` int(11) NOT NULL COMMENT '应用ID',
  PRIMARY KEY (`id`)
  INDEX INDEX_USER_ID (`user_id`)
  INDEX INDEX_APP_ID (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户应用表';

-- ----------------------------

-- Records of SYS_RE_USER_APP

-- ----------------------------

INSERT INTO `SYS_RE_USER_APP` VALUES ('9', '2', '81');
INSERT INTO `SYS_RE_USER_APP` VALUES ('10', '2', '82');
INSERT INTO `SYS_RE_USER_APP` VALUES ('11', '2', '1');

-- ----------------------------

-- Table structure for `SYS_RE_USER_ROLE`

-- ----------------------------

DROP TABLE IF EXISTS `SYS_RE_USER_ROLE`;
CREATE TABLE `SYS_RE_USER_ROLE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户ID ',
  `roleId` int(11) NOT NULL COMMENT '角色ID',
  `appId` int(11) NOT NULL COMMENT '应用ID',
  PRIMARY KEY (`id`),
  KEY `FK_SYS_RE_U_REFERENCE_SYS_USER` (`userId`),
  KEY `FK_SYS_RE_U_REFERENCE_SYS_ROLE` (`roleId`),
  KEY `FK_Reference_8` (`appId`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`appId`) REFERENCES `SYS_APP` (`id`),
  CONSTRAINT `FK_SYS_RE_U_REFERENCE_SYS_ROLE` FOREIGN KEY (`roleId`) REFERENCES `SYS_ROLE` (`id`),
  CONSTRAINT `FK_SYS_RE_U_REFERENCE_SYS_USER` FOREIGN KEY (`userId`) REFERENCES `SYS_USER` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------

-- Records of SYS_RE_USER_ROLE

-- ----------------------------

INSERT INTO `SYS_RE_USER_ROLE` VALUES ('7', '2', '4', '81');
INSERT INTO `SYS_RE_USER_ROLE` VALUES ('8', '2', '1', '1');
INSERT INTO `SYS_RE_USER_ROLE` VALUES ('9', '2', '5', '82');

