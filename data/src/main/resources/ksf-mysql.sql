SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for BS_SystemConfig
-- ----------------------------
DROP TABLE IF EXISTS `BS_SystemConfig`;
CREATE TABLE `BS_SystemConfig` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `value` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `default_val` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认值',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `val_type` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  UNIQUE KEY `IX_KEY` (`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for BS_Department
-- ----------------------------
DROP TABLE IF EXISTS `BS_Department`;
CREATE TABLE `BS_Department` (
  `did` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `alias` varchar(50) DEFAULT NULL COMMENT '部门别名',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `parent_did` int(11) DEFAULT NULL COMMENT '父级编号',
  `oid` int(11) DEFAULT NULL COMMENT '组织机构编号',
  PRIMARY KEY (`did`),
  KEY `fk_BS_Department_BS_Organization` (`oid`),
  CONSTRAINT `fk_BS_Department_BS_Organization` FOREIGN KEY (`oid`) REFERENCES `BS_Organization` (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_Department
-- ----------------------------
INSERT INTO `BS_Department` VALUES ('1', '总经办', 'General Manager Office', 'normal', '0', '1');
INSERT INTO `BS_Department` VALUES ('2', '技术部', 'Technology Department', 'normal', '0', '1');
INSERT INTO `BS_Department` VALUES ('3', '客服部', 'Service Department', 'normal', '0', '1');
INSERT INTO `BS_Department` VALUES ('4', '市场营销部', 'Sales & Marketing Department', 'normal', '0', '1');
INSERT INTO `BS_Department` VALUES ('5', '超级管理', 'Super Admin', 'normal', '0', '2');

-- ----------------------------
-- Table structure for BS_Organization
-- ----------------------------
DROP TABLE IF EXISTS `BS_Organization`;
CREATE TABLE `BS_Organization` (
  `oid` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织机构编号',
  `name` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `parent_oid` int(11) DEFAULT NULL COMMENT '父级编号',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_Organization
-- ----------------------------
INSERT INTO `BS_Organization` VALUES ('1', '中央平台', '0', 'normal', '2021-01-08 17:44:37');

-- ----------------------------
-- Table structure for BS_Permission
-- ----------------------------
DROP TABLE IF EXISTS `BS_Permission`;
CREATE TABLE `BS_Permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `alias` varchar(50) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL COMMENT '简介',
  `parent_rid` int(11) NOT NULL DEFAULT '0' COMMENT '父级编号',
  `level` varchar(255) DEFAULT NULL COMMENT '层级',
  `deep` tinyint(4) DEFAULT NULL COMMENT '深度',
  `idx` smallint(6) DEFAULT '0' COMMENT '排序',
  `classify` smallint(6) DEFAULT NULL COMMENT '权限归类',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_Permission
-- ----------------------------
INSERT INTO `BS_Permission` VALUES ('1', '用户管理', 'user_manage', '用户类型数据管理', '0', '0', '1', '0', '1', null);
INSERT INTO `BS_Permission` VALUES ('2', '用户注册', 'user_signup', null, '1', '0.1', '2', '1', '1', null);
INSERT INTO `BS_Permission` VALUES ('3', '用户删除', 'user_delete', null, '1', '0.1', '2', '2', '1', null);
INSERT INTO `BS_Permission` VALUES ('4', '用户编辑', 'user_update', null, '1', '0.1', '2', '3', '1', null);
INSERT INTO `BS_Permission` VALUES ('5', '用户查询', 'user_search', null, '1', '0.1', '2', '4', '1', null);
INSERT INTO `BS_Permission` VALUES ('6', '用户详情', 'user_detail', null, '1', '0.1', '2', '5', '1', null);

-- ----------------------------
-- Table structure for BS_Plugin
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin`;
CREATE TABLE `BS_Plugin` (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '插件编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '简介',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `enable` bit(1) DEFAULT NULL COMMENT '是否可实例化/购买',
  `avatar` varchar(100) DEFAULT NULL COMMENT '标志图',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `modified` datetime DEFAULT NULL COMMENT '最后修改时间',
  `owner` int(11) DEFAULT NULL COMMENT '插件拥有者（OID）',
  `modifier` int(11) DEFAULT NULL COMMENT '最后修改人',
  `author` int(11) DEFAULT NULL COMMENT '插件作者(UID)',
  `work_class` varchar(255) DEFAULT NULL COMMENT '执行此插件的后台工作类',
  `data_source_mode` varchar(10) DEFAULT NULL COMMENT '数据源模式(HTTP, Memory, etc..)',
  `report_mode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=50003 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for BS_Plugin_Http_Header
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin_Http_Header`;
CREATE TABLE `BS_Plugin_Http_Header` (
  `phhid` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `phrid` int(11) NOT NULL COMMENT '请求编号',
  `type` tinyint(4) DEFAULT NULL COMMENT '1:request 2:report',
  PRIMARY KEY (`phhid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for BS_Plugin_Http_Report
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin_Http_Report`;
CREATE TABLE `BS_Plugin_Http_Report` (
  `phrid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '插件报告编号',
  `url` varchar(100) DEFAULT NULL COMMENT '网址',
  `key` varchar(20) DEFAULT NULL COMMENT '报告键，多个键时根据键确定数据',
  `name` varchar(50) DEFAULT NULL COMMENT '报告名称',
  `method` varchar(10) DEFAULT NULL COMMENT 'HTTP Method',
  `poid` int(11) DEFAULT NULL COMMENT '插件编号',
  PRIMARY KEY (`phrid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for BS_Plugin_Http_Request
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin_Http_Request`;
CREATE TABLE `BS_Plugin_Http_Request` (
  `phrid` bigint(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL,
  `poid` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`phrid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for BS_Plugin_Org
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin_Org`;
CREATE TABLE `BS_Plugin_Org` (
  `poid` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL COMMENT '插件编号',
  `oid` int(11) NOT NULL COMMENT '组织机构编号',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `enable` bit(1) DEFAULT NULL COMMENT '当前组织机构实例化的此插件是否已启用',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`poid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for BS_Plugin_Org_Param
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin_Org_Param`;
CREATE TABLE `BS_Plugin_Org_Param` (
  `poid` int(11) NOT NULL COMMENT '插件编号',
  `pptid` int(11) NOT NULL COMMENT '插件参数模板编号',
  `val` varchar(255) DEFAULT NULL COMMENT '参数值',
  PRIMARY KEY (`poid`,`pptid`),
  UNIQUE KEY `IX_POID_PPTID` (`poid`,`pptid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for BS_Plugin_Param_Template
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin_Param_Template`;
CREATE TABLE `BS_Plugin_Param_Template` (
  `pptid` int(11) NOT NULL AUTO_INCREMENT COMMENT '插件参数模板编号',
  `pid` int(11) DEFAULT NULL COMMENT '插件编号',
  `key` varchar(50) DEFAULT NULL COMMENT '参数键',
  `type` varchar(50) DEFAULT NULL COMMENT '类型名称',
  `default_val` varchar(255) DEFAULT NULL COMMENT '默认值',
  `remark` varchar(255) DEFAULT NULL COMMENT '相关说明',
  `classify` int(11) DEFAULT NULL COMMENT '参数归类',
  PRIMARY KEY (`pptid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for BS_Plugin_Script_Rel
-- ----------------------------
DROP TABLE IF EXISTS `BS_Plugin_Script_Rel`;
CREATE TABLE `BS_Plugin_Script_Rel` (
  `pid` int(11) NOT NULL COMMENT '插件编号',
  `oid` bigint(20) NOT NULL COMMENT '命令编号',
  `index` smallint(6) DEFAULT NULL COMMENT '索引',
  `status` varchar(50) DEFAULT NULL COMMENT '插件中对应脚本的状态',
  `backup_oid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pid`,`oid`),
  UNIQUE KEY `IX_PID_OID` (`pid`,`oid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for BS_Role
-- ----------------------------
DROP TABLE IF EXISTS `BS_Role`;
CREATE TABLE `BS_Role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `did` int(11) DEFAULT NULL COMMENT '部门ID',
  `desc` varchar(255) DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`rid`),
  KEY `fk_BS_Role_BS_Department` (`did`),
  CONSTRAINT `fk_BS_Role_BS_Department` FOREIGN KEY (`did`) REFERENCES `BS_Department` (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_Role
-- ----------------------------
INSERT INTO `BS_Role` VALUES ('1', '总经理', '1', '最高权限');
INSERT INTO `BS_Role` VALUES ('2', '主管', '2', '技术部主管');
INSERT INTO `BS_Role` VALUES ('3', '服务人员', '3', '客服部店员');
INSERT INTO `BS_Role` VALUES ('4', '经理', '4', '市场营销部经理');

-- ----------------------------
-- Table structure for BS_RolePermRel
-- ----------------------------
DROP TABLE IF EXISTS `BS_RolePermRel`;
CREATE TABLE `BS_RolePermRel` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`rid`,`pid`),
  UNIQUE KEY `IX_GID_RID` (`rid`,`pid`),
  KEY `fk_BS_RolePermRel_BS_Permission` (`pid`),
  CONSTRAINT `fk_BS_RolePermRel_BS_Permission` FOREIGN KEY (`pid`) REFERENCES `BS_Permission` (`pid`),
  CONSTRAINT `fk_BS_RolePermRel_BS_Role` FOREIGN KEY (`rid`) REFERENCES `BS_Role` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_RolePermRel
-- ----------------------------

-- ----------------------------
-- Table structure for BS_Script
-- ----------------------------
DROP TABLE IF EXISTS `BS_Script`;
CREATE TABLE `BS_Script` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '命令集编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '简介',
  `category` int(11) DEFAULT NULL COMMENT '分类',
  `category_array` varchar(255) DEFAULT NULL COMMENT '分类数组',
  `category_names` varchar(255) DEFAULT NULL COMMENT '分类名称集合',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `res_url` varchar(255) DEFAULT NULL COMMENT '资源web路径',
  `status` varchar(30) DEFAULT NULL COMMENT '状态',
  `uid` int(11) DEFAULT NULL COMMENT '创建人',
  `oid` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '最后修改时间',
  `backup_sid` varchar(50) DEFAULT NULL COMMENT '在mongodb中备份的命令集ID',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for BS_Script_Category
-- ----------------------------
DROP TABLE IF EXISTS `BS_Script_Category`;
CREATE TABLE `BS_Script_Category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `parent_cid` int(11) DEFAULT NULL COMMENT '上层分类编号',
  `level` varchar(500) DEFAULT NULL COMMENT '层级',
  `deep` smallint(6) DEFAULT NULL COMMENT '深度',
  `oid` int(11) DEFAULT NULL COMMENT '公司编号',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for BS_Script_Tag
-- ----------------------------
DROP TABLE IF EXISTS `BS_Script_Tag`;
CREATE TABLE `BS_Script_Tag` (
  `tid` bigint(20) NOT NULL,
  `oid` bigint(20) NOT NULL,
  PRIMARY KEY (`tid`,`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_Script_Tag
-- ----------------------------

-- ----------------------------
-- Table structure for BS_Tag
-- ----------------------------
DROP TABLE IF EXISTS `BS_Tag`;
CREATE TABLE `BS_Tag` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `color` varchar(15) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_Tag
-- ----------------------------

-- ----------------------------
-- Table structure for BS_Task
-- ----------------------------
DROP TABLE IF EXISTS `BS_Task`;
CREATE TABLE `BS_Task` (
  `tid` bigint(20) NOT NULL COMMENT '任务编号',
  `time_type` tinyint(4) DEFAULT NULL COMMENT '时间类型（0：即时任务 1: 定时任务）',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `is_cycle` bit(1) DEFAULT NULL COMMENT '是否周期任务',
  `next_run_time` datetime DEFAULT NULL COMMENT '下次运行时间',
  `cron_exp` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of BS_Task
-- ----------------------------

-- ----------------------------
-- Table structure for BS_User
-- ----------------------------
DROP TABLE IF EXISTS `BS_User`;
CREATE TABLE `BS_User` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `nick` varchar(50) DEFAULT NULL COMMENT '昵称',
  `name` varchar(50) DEFAULT NULL COMMENT '名字',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `status` varchar(20) DEFAULT NULL COMMENT '状态',
  `is_super` bit(1) DEFAULT b'0' COMMENT '是否超级管理员',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `did` int(11) DEFAULT NULL COMMENT '部门编号',
  `last_online` datetime DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';


-- ----------------------------
-- Table structure for BS_UserPermRel
-- ----------------------------
DROP TABLE IF EXISTS `BS_UserPermRel`;
CREATE TABLE `BS_UserPermRel` (
  `uid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`pid`),
  UNIQUE KEY `IX_UID_RID` (`uid`,`pid`),
  KEY `fk_BS_UserPermRel_BS_Permission` (`pid`),
  CONSTRAINT `fk_BS_UserPermRel_BS_Permission` FOREIGN KEY (`pid`) REFERENCES `BS_Permission` (`pid`),
  CONSTRAINT `fk_BS_UserPermRel_BS_User` FOREIGN KEY (`uid`) REFERENCES `BS_User` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for BS_UserRoleRel
-- ----------------------------
DROP TABLE IF EXISTS `BS_UserRoleRel`;
CREATE TABLE `BS_UserRoleRel` (
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`),
  UNIQUE KEY `IX_UID_GID` (`uid`,`rid`) USING BTREE,
  KEY `fk_BS_UserRoleRel_BS_Role` (`rid`),
  CONSTRAINT `fk_BS_UserRoleRel_BS_Role` FOREIGN KEY (`rid`) REFERENCES `BS_Role` (`rid`),
  CONSTRAINT `fk_BS_UserRoleRel_BS_User` FOREIGN KEY (`uid`) REFERENCES `BS_User` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;