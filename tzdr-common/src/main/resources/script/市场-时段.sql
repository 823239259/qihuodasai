DROP TABLE IF EXISTS `w_date_calculated`;

CREATE TABLE `w_date_calculated` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `d_hour` varchar(11) DEFAULT NULL,
  `start_hour` time DEFAULT NULL,
  `end_hour` time DEFAULT NULL,
  `combination` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of w_date_calculated
-- ----------------------------
INSERT INTO `w_date_calculated` VALUES ('1', '01', '01:00:00', '02:00:00', '1', '1');
INSERT INTO `w_date_calculated` VALUES ('10', '10', '10:00:00', '11:00:00', '10', '10');
INSERT INTO `w_date_calculated` VALUES ('11', '11', '11:00:00', '12:00:00', '11', '11');
INSERT INTO `w_date_calculated` VALUES ('12', '12', '12:00:00', '13:00:00', '12', '12');
INSERT INTO `w_date_calculated` VALUES ('13', '13', '13:00:00', '14:00:00', '13', '13');
INSERT INTO `w_date_calculated` VALUES ('14', '14', '14:00:00', '15:00:00', '14', '14');
INSERT INTO `w_date_calculated` VALUES ('15', '15', '15:00:00', '16:00:00', '15', '15');
INSERT INTO `w_date_calculated` VALUES ('16', '16', '16:00:00', '17:00:00', '16', '16');
INSERT INTO `w_date_calculated` VALUES ('17', '17', '17:00:00', '18:00:00', '17', '17');
INSERT INTO `w_date_calculated` VALUES ('18', '18', '18:00:00', '19:00:00', '18', '18');
INSERT INTO `w_date_calculated` VALUES ('19', '19', '19:00:00', '20:00:00', '19', '19');
INSERT INTO `w_date_calculated` VALUES ('2', '02', '02:00:00', '03:00:00', '2', '2');
INSERT INTO `w_date_calculated` VALUES ('20', '20', '20:00:00', '21:00:00', '20', '20');
INSERT INTO `w_date_calculated` VALUES ('21', '21', '21:00:00', '22:00:00', '21', '21');
INSERT INTO `w_date_calculated` VALUES ('22', '22', '22:00:00', '23:00:00', '22', '22');
INSERT INTO `w_date_calculated` VALUES ('23', '23', '23:00:00', '00:00:00', '23', '23');
INSERT INTO `w_date_calculated` VALUES ('24', '00', '00:00:00', '01:00:00', '24', '24');
INSERT INTO `w_date_calculated` VALUES ('3', '03', '03:00:00', '04:00:00', '3', '3');
INSERT INTO `w_date_calculated` VALUES ('4', '04', '04:00:00', '05:00:00', '4', '4');
INSERT INTO `w_date_calculated` VALUES ('5', '05', '05:00:00', '06:00:00', '5', '5');
INSERT INTO `w_date_calculated` VALUES ('6', '06', '06:00:00', '07:00:00', '6', '6');
INSERT INTO `w_date_calculated` VALUES ('7', '07', '07:00:00', '08:00:00', '7', '7');
INSERT INTO `w_date_calculated` VALUES ('8', '08', '08:00:00', '09:00:00', '8', '8');
INSERT INTO `w_date_calculated` VALUES ('9', '09', '09:00:00', '10:00:00', '9', '9');