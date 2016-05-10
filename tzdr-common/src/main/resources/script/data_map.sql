/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : testcommon

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2015-01-21 15:09:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `data_map`
-- ----------------------------
DROP TABLE IF EXISTS `data_map`;
CREATE TABLE `data_map` (
  `id` varchar(32) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `type_key` varchar(100) DEFAULT NULL,
  `type_name` varchar(300) DEFAULT NULL,
  `value_key` varchar(100) DEFAULT NULL,
  `value_name` varchar(300) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of data_map
-- ----------------------------
INSERT INTO `data_map` VALUES ('112', '', null, 'parentAccountType', '母账户类型', 'trust', '信托', '1');
INSERT INTO `data_map` VALUES ('1211', '', null, 'drawStatus', '提现记录状态', '31', '提现成功', null);
INSERT INTO `data_map` VALUES ('1215', '', null, 'drawStatus', '提现记录状态', '22', '汇付提现成功', null);
INSERT INTO `data_map` VALUES ('1221', '', null, 'drawStatus', '提现记录状态', '11', '等待处理', null);
INSERT INTO `data_map` VALUES ('1225', '', null, 'drawStatus', '提现记录状态', '21', '提现处理中', null);
INSERT INTO `data_map` VALUES ('123', '', null, 'userType', '用户类型', 'matchEndowment', '配资用户', null);
INSERT INTO `data_map` VALUES ('1232', '', null, 'drawStatus', '提现记录状态', '4', '汇付提现失败', null);
INSERT INTO `data_map` VALUES ('1331', '', null, 'drawStatus', '提现记录状态', '3', '已取消', null);
INSERT INTO `data_map` VALUES ('1332', '', null, 'drawStatus', '提现记录状态', '2', '无效', null);
INSERT INTO `data_map` VALUES ('1340', '', null, 'drawStatus', '提现记录状态', '1', '失败', null);
INSERT INTO `data_map` VALUES ('222', '', null, 'securitiesBusiness', '券商', 'haitong', '海通证券', '1');
INSERT INTO `data_map` VALUES ('2223', '', null, 'securitiesBusiness', '券商', 'guotai', '国泰证券', '7');
INSERT INTO `data_map` VALUES ('2234', '', null, 'securitiesBusiness', '券商', 'huatai', '华泰证券', '8');
INSERT INTO `data_map` VALUES ('2243', '', null, 'parentAccountType', '母账户类型', 'ownFunds', '自有资金', '2');
INSERT INTO `data_map` VALUES ('2312', '', null, 'limitStatus', '限制状态', 'false', '未限制', '2');
INSERT INTO `data_map` VALUES ('232', '', null, 'securitiesBusiness', '券商', 'guangfa', '广发证券', '5');
INSERT INTO `data_map` VALUES ('2332', '', null, 'limitStatus', '限制状态', 'true', '已限制', '1');
INSERT INTO `data_map` VALUES ('234', '', null, 'parentAccountType', '母账户类型', 'folk', '民间', '3');
INSERT INTO `data_map` VALUES ('2344', '', null, 'callNoticeStatus', '电话通知状态', '0', '未通知', null);
INSERT INTO `data_map` VALUES ('322', '', null, 'securitiesBusiness', '券商', 'minmetals', '五矿证券', '4');
INSERT INTO `data_map` VALUES ('3222', '', null, 'securitiesBusiness', '券商', 'huaxi', '华西证券', '2');
INSERT INTO `data_map` VALUES ('333', '', null, 'userType', '用户类型', 'regist', '注册用户', null);
INSERT INTO `data_map` VALUES ('345', '', null, 'userfundType', '用户资金记录类型', '11', '利息支出', null);
INSERT INTO `data_map` VALUES ('346', '', null, 'userfundType', '用户资金记录类型', '12', '管理费支出', null);
INSERT INTO `data_map` VALUES ('356', '', null, 'parentAccountType', '母账户类型', 'securitiesFinancing', '融资融券', '5');
INSERT INTO `data_map` VALUES ('432', '', null, 'callNoticeStatus', '电话通知状态', '1', '已通知', null);
INSERT INTO `data_map` VALUES ('545', '', null, 'callNoticeStatus', '电话通知状态', '2', '未接通', null);
INSERT INTO `data_map` VALUES ('ww123', '', '0', 'userType', '用户类型', 'matchEndowment', '配资用户', null);
INSERT INTO `data_map` VALUES ('ww12345', '', '0', 'paystatus', '充值状态', '1', '失败', null);
INSERT INTO `data_map` VALUES ('ww123456', null, null, 'paystatus', '充值状态', '2', '无效', null);
INSERT INTO `data_map` VALUES ('ww1345', '', null, 'userfundType', '用户资金记录类型', '11', '利息支出', null);
INSERT INTO `data_map` VALUES ('ww1346', '', null, 'userfundType', '用户资金记录类型', '12', '管理费支出', null);
INSERT INTO `data_map` VALUES ('ww2345', null, null, 'paystatus', '充值状态', '11', '充值中', null);
INSERT INTO `data_map` VALUES ('ww301', null, null, 'bankname', '银行名称', 'ccb', '中国建设银行', null);
INSERT INTO `data_map` VALUES ('ww303', null, null, 'bankname', '银行名称', 'icbc', '中国工商银行', null);
INSERT INTO `data_map` VALUES ('ww304', null, null, 'bankname', '银行名称', 'boc', '中国银行', null);
INSERT INTO `data_map` VALUES ('ww306', null, null, 'bankname', '银行名称', 'cmb', '招商银行', null);
INSERT INTO `data_map` VALUES ('ww307', null, null, 'bankname', '银行名称', 'cmbc', '中国民生银行', null);
INSERT INTO `data_map` VALUES ('ww308', null, null, 'bankname', '银行名称', 'spdb', '浦发银行', null);
INSERT INTO `data_map` VALUES ('ww309', null, null, 'bankname', '银行名称', 'gdb', '广发银行', null);
INSERT INTO `data_map` VALUES ('ww310', null, null, 'bankname', '银行名称', 'hxb', '华夏银行', null);
INSERT INTO `data_map` VALUES ('ww311', null, null, 'bankname', '银行名称', 'psbc', '中国邮政储蓄银行', null);
INSERT INTO `data_map` VALUES ('ww312', null, null, 'bankname', '银行名称', 'ceb', '光大银行', null);
INSERT INTO `data_map` VALUES ('ww313', null, null, 'bankname', '银行名称', 'bea', '东亚银行', null);
INSERT INTO `data_map` VALUES ('ww314', null, null, 'bankname', '银行名称', 'cib', '兴业银行', null);
INSERT INTO `data_map` VALUES ('ww315', null, null, 'bankname', '银行名称', 'comm', '交通银行', null);
INSERT INTO `data_map` VALUES ('ww316', null, null, 'bankname', '银行名称', 'citic', '中信银行', null);
INSERT INTO `data_map` VALUES ('ww317', null, null, 'bankname', '银行名称', 'bja', '北京银行', null);
INSERT INTO `data_map` VALUES ('ww318', null, null, 'bankname', '银行名称', 'shrcb', '上海农商银行', null);
INSERT INTO `data_map` VALUES ('ww319', null, null, 'bankname', '银行名称', 'wzcb', '温州银行', null);
INSERT INTO `data_map` VALUES ('ww333', '', '0', 'userType', '用户类型', 'regist', '注册用户', null);
INSERT INTO `data_map` VALUES ('ww343', null, null, 'paytype', '充值类型', '4', '银行转账', null);
INSERT INTO `data_map` VALUES ('ww345', null, null, 'paystatus', '充值状态', '12', '待确认', null);
INSERT INTO `data_map` VALUES ('ww350', null, null, 'paystatus', '充值状态', '0', '未处理', null);
INSERT INTO `data_map` VALUES ('ww433', null, null, 'paystatus', '充值状态', '21', '充值成功', null);
INSERT INTO `data_map` VALUES ('ww45', null, null, 'paytype', '充值类型', '1', '快捷支付', null);
INSERT INTO `data_map` VALUES ('ww46', null, null, 'paytype', '充值类型', '2', '网银支付', null);
INSERT INTO `data_map` VALUES ('ww47', null, null, 'paytype', '充值类型', '3', '支付宝', null);
INSERT INTO `data_map` VALUES ('ww801', null, null, 'rechargeStatus', '充值状态', '2', '已充值', null);
INSERT INTO `data_map` VALUES ('ww802', null, null, 'rechargeStatus', '充值状态', '1', '未充值', null);
INSERT INTO `data_map` VALUES ('82e33502a36f11e4a756000c29c15e71', 0, 1, 'Education', '最高学历', 'SeniorHighSchool', '高中', 3);
INSERT INTO `data_map` VALUES ('82e34757a36f11e4a756000c29c15e71', 0, 1, 'Education', '最高学历', 'College', '大学', 2);
INSERT INTO `data_map` VALUES ('82e35bf9a36f11e4a756000c29c15e71', 0, 0, 'Education', '最高学历', 'Master', '硕士', 1);
INSERT INTO `data_map` VALUES ('82e38dd3a36f11e4a756000c29c15e71', 0, 1, 'Education', '最高学历', 'MiddleSchool', '初中', 4);
INSERT INTO `data_map` VALUES ('82e39452a36f11e4a756000c29c15e71', 0, 1, 'Education', '最高学历', 'PrimarySchool', '小学', 5);
INSERT INTO `data_map` VALUES ('82e39559a36f11e4a756000c29c15e71', 0, 1, 'Education', '最高学历', 'Other', '其他', 6);
INSERT INTO `data_map` VALUES ('82e3580ea36f11e4a756000c29c15e71', 0, 1, 'Income', '月收入', '6', '20000以上', 1);
INSERT INTO `data_map` VALUES ('82e35b02a36f11e4a756000c29c15e71', 0, 1, 'Income', '月收入', '3', '10001-20000', 2);
INSERT INTO `data_map` VALUES ('82e35d11a36f11e4a756000c29c15e71', 0, 1, 'Income', '月收入', '2', '5001-10000', 3);
INSERT INTO `data_map` VALUES ('82e36574a36f11e4a756000c29c15e71', 0, 1, 'Income', '月收入', '1', '5000以下', 4);
INSERT INTO `data_map` VALUES ('82e33614a36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'Manufacturing', '制造业', 4);
INSERT INTO `data_map` VALUES ('82e3371da36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'Diffuse', '传播业', 5);
INSERT INTO `data_map` VALUES ('82e33829a36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'Education', '教育', 6);
INSERT INTO `data_map` VALUES ('82e33cdda36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'Government', '政府机构', 7);
INSERT INTO `data_map` VALUES ('82e340eda36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'HealthCare', '医疗保健', 8);
INSERT INTO `data_map` VALUES ('82e34209a36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'RealEstate', '房地产', 9);
INSERT INTO `data_map` VALUES ('82e3431fa36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'Other', '其他', 10);
INSERT INTO `data_map` VALUES ('82e3965aa36f11e4a756000c29c15e71', 0, 0, 'Industry', '所属行业', 'Financial', '金融业', 1);
INSERT INTO `data_map` VALUES ('82e39758a36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'Service', '服务业', 2);
INSERT INTO `data_map` VALUES ('82e39af5a36f11e4a756000c29c15e71', 0, 1, 'Industry', '所属行业', 'Information', '信息产业', 3);
INSERT INTO `data_map` VALUES ('82e3802fa36f11e4a756000c29c15e71', 0, 1, 'Marriage', '婚姻状况', '0', '暂不填写', 0);
INSERT INTO `data_map` VALUES ('82e38149a36f11e4a756000c29c15e71', 0, 0, 'Marriage', '婚姻状况', '1', '未婚', 1);
INSERT INTO `data_map` VALUES ('82e3836fa36f11e4a756000c29c15e71', 0, 1, 'Marriage', '婚姻状况', '2', '已婚', 2);
INSERT INTO `data_map` VALUES ('82e34425a36f11e4a756000c29c15e71', 0, 1, 'Position', '职位', 'Student', '学生', 6);
INSERT INTO `data_map` VALUES ('82e3452da36f11e4a756000c29c15e71', 0, 1, 'Position', '职位', 'Employee', '公司职员', 2);
INSERT INTO `data_map` VALUES ('82e34638a36f11e4a756000c29c15e71', 0, 1, 'Position', '职位', 'Manager', '私营业主/职业经理人', 1);
INSERT INTO `data_map` VALUES ('82e34f15a36f11e4a756000c29c15e71', 0, 1, 'Position', '职位', 'CivilServant', '公务员', 3);
INSERT INTO `data_map` VALUES ('82e351afa36f11e4a756000c29c15e71', 0, 1, 'Position', '职位', 'FreelanceWork', '自由职业', 4);
INSERT INTO `data_map` VALUES ('82e35611a36f11e4a756000c29c15e71', 0, 1, 'Position', '职位', 'Retire', '退休', 5);
INSERT INTO `data_map` VALUES ('82e35713a36f11e4a756000c29c15e71', 0, 1, 'Position', '职位', 'Other', '其他', 7);
INSERT INTO `data_map` VALUES ('b4de5361b4d911e4887328d244d41b35', 0, 0, 'Message', '留言类型', 'Feedback', '意见反馈', 1);
INSERT INTO `data_map` VALUES ('b4ecf7f1b4d911e4887328d244d41b35', 0, 0, 'Message', '留言类型', 'Function', '功能使用问题', 3);
INSERT INTO `data_map` VALUES ('b4f618cdb4d911e4887328d244d41b35', 0, 0, 'Message', '留言类型', 'WithInformation', '随心配问题', 2);
INSERT INTO `data_map` VALUES ('b5007e99b4d911e4887328d244d41b35', 0, 0, 'Message', '留言类型', 'Other', '其它', 4);
