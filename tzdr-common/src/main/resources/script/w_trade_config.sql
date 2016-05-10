/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : testcommon

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2015-01-21 16:24:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `w_trade_config`
-- ----------------------------
DROP TABLE IF EXISTS `w_trade_config`;
CREATE TABLE `w_trade_config` (
  `id` varchar(32) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `multiple_range_end` int(11) DEFAULT NULL,
  `multiple_range_start` int(11) DEFAULT NULL,
  `daily_interest` double DEFAULT NULL,
  `daily_management_fee` double DEFAULT NULL,
  `day_range_end` int(11) DEFAULT NULL,
  `day_range_start` int(11) DEFAULT NULL,
  `deposit_range_end` double DEFAULT NULL,
  `deposit_range_start` double DEFAULT NULL,
  `year_interest` double DEFAULT NULL,
  `year_management_fee` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of w_trade_config
-- ----------------------------
INSERT INTO `w_trade_config` VALUES ('1', '', '0', '5', '1', '0.00072', '0.00039', '10', '2', '100000', '100', '0.26', '0.14');
INSERT INTO `w_trade_config` VALUES ('10', '', '0', '5', '1', '0.00061', '0.00033', '10', '2', '3000000', '1200000', '0.22', '0.12');
INSERT INTO `w_trade_config` VALUES ('11', '', '0', '5', '1', '0.00064', '0.00034', '30', '11', '100000', '100', '0.23', '0.12');
INSERT INTO `w_trade_config` VALUES ('12', '', '0', '10', '6', '0.00081', '0.00044', '30', '11', '100000', '100', '0.29', '0.16');
INSERT INTO `w_trade_config` VALUES ('13', '', '0', '15', '11', '0.001', '0.00053', '30', '11', '100000', '100', '0.36', '0.19');
INSERT INTO `w_trade_config` VALUES ('14', '', '0', '5', '1', '0.00058', '0.00032', '30', '11', '400000', '100000', '0.21', '0.12');
INSERT INTO `w_trade_config` VALUES ('15', '', '0', '10', '6', '0.00078', '0.00042', '30', '11', '400000', '100000', '0.28', '0.15');
INSERT INTO `w_trade_config` VALUES ('16', '', '0', '15', '11', '0.00096', '0.00052', '30', '11', '400000', '100000', '0.34', '0.19');
INSERT INTO `w_trade_config` VALUES ('17', '', '0', '5', '1', '0.00056', '0.0003', '30', '11', '1200000', '400000', '0.2', '0.11');
INSERT INTO `w_trade_config` VALUES ('18', '', '0', '10', '6', '0.00074', '0.0004', '30', '11', '1200000', '400000', '0.27', '0.14');
INSERT INTO `w_trade_config` VALUES ('19', '', '0', '15', '11', '0.00092', '0.0005', '30', '11', '1200000', '400000', '0.33', '0.18');
INSERT INTO `w_trade_config` VALUES ('2', '', '0', '10', '6', '0.00092', '0.00049', '10', '2', '100000', '100', '0.33', '0.18');
INSERT INTO `w_trade_config` VALUES ('20', '', '0', '5', '1', '0.00052', '0.00028', '30', '11', '3000000', '1200000', '0.19', '0.1');
INSERT INTO `w_trade_config` VALUES ('21', '', '0', '5', '1', '0.00058', '0.00031', '60', '31', '100000', '100', '0.21', '0.11');
INSERT INTO `w_trade_config` VALUES ('22', '', '0', '10', '6', '0.00078', '0.00042', '60', '31', '100000', '100', '0.28', '0.15');
INSERT INTO `w_trade_config` VALUES ('23', '', '0', '15', '11', '0.00098', '0.00053', '60', '31', '100000', '100', '0.35', '0.19');
INSERT INTO `w_trade_config` VALUES ('24', '', '0', '5', '1', '0.00054', '0.00029', '60', '31', '400000', '100000', '0.2', '0.11');
INSERT INTO `w_trade_config` VALUES ('25', '', '0', '10', '6', '0.00074', '0.0004', '60', '31', '400000', '100000', '0.27', '0.14');
INSERT INTO `w_trade_config` VALUES ('26', '', '0', '15', '11', '0.00094', '0.00051', '60', '31', '400000', '100000', '0.34', '0.18');
INSERT INTO `w_trade_config` VALUES ('27', '', '0', '5', '1', '0.00051', '0.00027', '60', '31', '1200000', '400000', '0.18', '0.1');
INSERT INTO `w_trade_config` VALUES ('28', '', '0', '10', '6', '0.0007', '0.00038', '60', '31', '1200000', '400000', '0.25', '0.14');
INSERT INTO `w_trade_config` VALUES ('29', '', '0', '15', '11', '0.0009', '0.00049', '60', '31', '1200000', '400000', '0.33', '0.18');
INSERT INTO `w_trade_config` VALUES ('3', '', '0', '15', '11', '0.00108', '0.00058', '10', '2', '100000', '100', '0.39', '0.21');
INSERT INTO `w_trade_config` VALUES ('30', '', '0', '5', '1', '0.00047', '0.00025', '60', '31', '3000000', '1200000', '0.17', '0.09');
INSERT INTO `w_trade_config` VALUES ('31', '', '0', '5', '1', '0.00054', '0.00029', '120', '61', '100000', '100', '0.2', '0.11');
INSERT INTO `w_trade_config` VALUES ('32', '', '0', '10', '6', '0.00076', '0.00041', '120', '61', '100000', '100', '0.27', '0.15');
INSERT INTO `w_trade_config` VALUES ('33', '', '0', '15', '11', '0.00098', '0.00053', '120', '61', '100000', '100', '0.35', '0.19');
INSERT INTO `w_trade_config` VALUES ('34', '', '0', '5', '1', '0.00051', '0.00027', '120', '61', '400000', '100000', '0.18', '0.1');
INSERT INTO `w_trade_config` VALUES ('35', '', '0', '10', '6', '0.00072', '0.00039', '120', '61', '400000', '100000', '0.26', '0.14');
INSERT INTO `w_trade_config` VALUES ('36', '', '0', '15', '11', '0.00094', '0.00051', '120', '61', '400000', '100000', '0.34', '0.18');
INSERT INTO `w_trade_config` VALUES ('37', '', '0', '5', '1', '0.00047', '0.00025', '120', '61', '1200000', '400000', '0.17', '0.09');
INSERT INTO `w_trade_config` VALUES ('38', '', '0', '10', '6', '0.00069', '0.00037', '120', '61', '1200000', '400000', '0.25', '0.13');
INSERT INTO `w_trade_config` VALUES ('39', '', '0', '15', '11', '0.0009', '0.00049', '120', '61', '1200000', '400000', '0.33', '0.18');
INSERT INTO `w_trade_config` VALUES ('4', '', '0', '5', '1', '0.00069', '0.00037', '10', '2', '400000', '100000', '0.25', '0.13');
INSERT INTO `w_trade_config` VALUES ('40', '', '0', '5', '1', '0.00043', '0.00023', '120', '61', '3000000', '1200000', '0.16', '0.08');
INSERT INTO `w_trade_config` VALUES ('41', '', '0', '5', '1', '0.00051', '0.00027', '180', '121', '100000', '100', '0.18', '0.1');
INSERT INTO `w_trade_config` VALUES ('42', '', '0', '10', '6', '0.00072', '0.00039', '180', '121', '100000', '100', '0.26', '0.14');
INSERT INTO `w_trade_config` VALUES ('43', '', '0', '15', '11', '0.00094', '0.00051', '180', '121', '100000', '100', '0.34', '0.18');
INSERT INTO `w_trade_config` VALUES ('44', '', '0', '5', '1', '0.00047', '0.00025', '180', '121', '400000', '100000', '0.17', '0.09');
INSERT INTO `w_trade_config` VALUES ('45', '', '0', '10', '6', '0.00069', '0.00037', '180', '121', '400000', '100000', '0.25', '0.13');
INSERT INTO `w_trade_config` VALUES ('46', '', '0', '15', '11', '0.0009', '0.00049', '180', '121', '400000', '100000', '0.33', '0.18');
INSERT INTO `w_trade_config` VALUES ('47', '', '0', '5', '1', '0.00043', '0.00023', '180', '121', '1200000', '400000', '0.16', '0.08');
INSERT INTO `w_trade_config` VALUES ('48', '', '0', '10', '6', '0.00065', '0.00035', '180', '121', '1200000', '400000', '0.23', '0.13');
INSERT INTO `w_trade_config` VALUES ('49', '', '0', '15', '11', '0.00087', '0.00047', '180', '121', '1200000', '400000', '0.31', '0.17');
INSERT INTO `w_trade_config` VALUES ('5', '', '0', '10', '6', '0.00086', '0.00047', '10', '2', '400000', '100000', '0.31', '0.17');
INSERT INTO `w_trade_config` VALUES ('50', '', '0', '5', '1', '0.0004', '0.00021', '180', '121', '3000000', '1200000', '0.14', '0.08');
INSERT INTO `w_trade_config` VALUES ('6', '', '0', '15', '11', '0.00106', '0.00056', '10', '2', '400000', '100000', '0.38', '0.2');
INSERT INTO `w_trade_config` VALUES ('7', '', '0', '5', '1', '0.00064', '0.00035', '10', '2', '1200000', '400000', '0.23', '0.13');
INSERT INTO `w_trade_config` VALUES ('8', '', '0', '10', '6', '0.00083', '0.00045', '10', '2', '1200000', '400000', '0.3', '0.16');
INSERT INTO `w_trade_config` VALUES ('9', '', '0', '15', '11', '0.001', '0.00054', '10', '2', '1200000', '400000', '0.36', '0.2');
