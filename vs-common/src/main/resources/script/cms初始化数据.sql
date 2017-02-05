-- -------------------------------
-- sys_resource 初始数据
-- -------------------------------
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('1','sys', '资源', '0', '0/', '', null);
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('2','usermanager', '系统管理', '1', '0/1/', '', null);
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('3','rolemanager', '系统权限管理', '1', '0/1/', '', null);
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('4','userlist', '用户列表', '2', '0/1/2/', '', 'admin/user/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('5','orglist', '组织列表', '2', '0/1/2/', '', 'admin/org/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('6','permisionlist', '权限列表', '3', '0/1/3/', '', 'admin/permission/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('7','rolelist', '角色列表', '3', '0/1/3/', '', 'admin/role/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('8','grantuser', '授权角色给用户', '3', '0/1/3/', '', 'admin/permission/toGrantRole');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('9','accountmanager', '恒生账户管理', '1', '0/1', '', null);
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('10','subaccount', '子账户列表', '9', '0/1/9/', '', 'admin/subAccount/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('11','parentaccount', '母账户列表', '9', '0/1/9/', '', 'admin/parentAccount/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('12','finance', '财务管理', '1', '0/1/', '', null);
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('13','withdrawal', '提现记录', '12', '0/1/12/', '', 'admin/withdrawal/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('14','tradeday', '交易日维护', '2', '0/1/2/', '', 'admin/tradeDay/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('15','marginremind', '补仓提醒方案', '16', '0/1/16/', '', 'admin/marginRemind/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('16','riskmanager', '风控管理', '1', '0/1/', '', null);
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('17','notEnoughBalance', '余额不足提醒', '16', '0/1/16/', '', 'admin/notEnoughBalance/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('18','monitor', '方案监控', '16', '0/1/16/', '', 'admin/monitor/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('19','operationalConfig', '运营维护', '1', '0/1/', '',null);
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('20','friendlyLink', '友情连接', '19', '0/1/19/', '', 'admin/config/friendlyLink/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('21','newsColumn', '新闻栏目', '19', '0/1/19/', '', 'admin/config/newsColumn/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('22','banner', 'banner维护', '19', '0/1/19/', '', 'admin/config/banner/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('23','news', '新闻', '19', '0/1/19/', '', 'admin/config/news/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('24','partners', '合作伙伴', '19', '0/1/19/', '', 'admin/config/partners/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('25','wuser', '用户列表', '9', '0/1/9/', '', 'admin/wUser/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('26','wuser', '用户实名认证', '9', '0/1/9/', '', 'admin/wUser/listCheck');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('27','wuser', '充值审核', '9', '0/1/9/', '', 'admin/recharge/list');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('28','wuser', '手工现金充值', '9', '0/1/9/', '', 'admin/recharge/handlerList');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('29','wuser', '充值记录', '9', '0/1/9/', '', 'admin/recharge/rechargeQuery');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('30','wuser', '代理商维护', '9', '0/1/9/', '', 'admin/wUser/listAgents');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('31','wuser', '设置预警值', '9', '0/1/9/', '', 'admin/wUser/listSettingWarning');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('32','wuser', '欠费待终结方案', '9', '0/1/9/', '', 'admin/wUser/listEnd');
INSERT INTO `sys_resource` (id,identity,name,parent_id,parent_ids,is_show,url)  VALUES ('33','tradeConfig', '配资参数维护', '2', '0/1/2/', '', 'admin/tradeConfig/list');


-- ----------------------------------------
-- sys_organization初始数据
-- ----------------------------------------
INSERT INTO `sys_organization` (id,code,deleted,name,parent_id,is_show,weight) VALUES ('1','001', '', '上海信闳', '0', '', '1');
-- ----------------------------------------
-- sys_user 初始数据
-- ----------------------------------------
INSERT INTO `sys_user` (id,admin,deleted,email,password,salt,status,username,organization_id) VALUES ('1','', '', 'xinhong@tzdr.com','9f3cb9c56447de899cbb23024a64edba', '1X2mm2oYKy', 'normal', 'admin','1');

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` (id,description,name,permission,is_show) VALUES ('1','所有数据操作的权限', '所有', '*', '');
INSERT INTO `sys_permission` (id,description,name,permission,is_show) VALUES ('2','新增数据操作的权限', '新增', 'create', '');
INSERT INTO `sys_permission` (id,description,name,permission,is_show) VALUES ('3','修改数据操作的权限', '修改', 'update', '');
INSERT INTO `sys_permission` (id,description,name,permission,is_show) VALUES ('4','删除数据操作的权限', '删除', 'delete', '');
INSERT INTO `sys_permission` (id,description,name,permission,is_show) VALUES ('5','查看数据操作的权限', '查看', 'view', '');
INSERT INTO `sys_permission` (id,description,name,permission,is_show) VALUES ('6','审核数据操作的权限', '审核', 'audit', '');
INSERT INTO `sys_permission` (id,description,name,permission,is_show) VALUES ('7','重置用户密码的权限', '重置密码', 'reset', '');


-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` (id,deleted,description,name,role,is_show) VALUES ('1','', '系统管理', '系统管理员', 'admin', '');

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` (id,role_ids,user_id) VALUES ('1','1', '1');


-- ----------------------------
-- Records of sys_role_resource_permission
-- ----------------------------
INSERT INTO `sys_role_resource_permission` (permission_ids,resource_id,role_id) VALUES ('1', '3', '1');
INSERT INTO `sys_role_resource_permission` (permission_ids,resource_id,role_id) VALUES ('1', '2', '1');
INSERT INTO `sys_role_resource_permission` (permission_ids,resource_id,role_id) VALUES ('1', '9', '1');
INSERT INTO `sys_role_resource_permission` (permission_ids,resource_id,role_id) VALUES ('1', '12', '1');
INSERT INTO `sys_role_resource_permission` (permission_ids,resource_id,role_id) VALUES ('1', '16', '1');
INSERT INTO `sys_role_resource_permission` (permission_ids,resource_id,role_id) VALUES ('1', '19', '1');