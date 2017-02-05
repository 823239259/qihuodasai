DROP TABLE IF EXISTS `w_generalize_channel`;

CREATE TABLE `w_generalize_channel` (
  `id` varchar(32) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `createdate` bigint(20) DEFAULT NULL,
  `param` varchar(255) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url_key` varchar(255) DEFAULT NULL,
  `type_one_param` varchar(255) DEFAULT NULL,
  `type_one_title` varchar(255) DEFAULT NULL,
  `type_two_param` varchar(255) DEFAULT NULL,
  `type_two_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of w_generalize_channel
-- ----------------------------
INSERT INTO `w_generalize_channel` VALUES ('1', '', '0', '1425275648', '010010001', '1', '什么是配资（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('10', '', '0', '1425275648', '010010010', '10', '炒股怎么开户（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('100', '', '0', '1425275648', '040020002', '5', '策略2（付费）', null, null, '品友DSP', null, '熊熊科技DSP');
INSERT INTO `w_generalize_channel` VALUES ('101', '', '0', '1425275648', '040020003', '6', '策略3（付费）', null, null, '品友DSP', null, '熊熊科技DSP');
INSERT INTO `w_generalize_channel` VALUES ('102', '', '0', '1425275648', '050010001', '1', '114导航（付费）', null, null, '外链', null, '导航网站');
INSERT INTO `w_generalize_channel` VALUES ('103', '', '0', '1425275648', '050010002', '2', 'hao123导航（付费）', null, null, '外链', null, '导航网站');
INSERT INTO `w_generalize_channel` VALUES ('104', '', '0', '1425275648', '050020001', '3', '第一金/小额贷款', null, null, '外链', null, '友情链接');
INSERT INTO `w_generalize_channel` VALUES ('105', '', '0', '1425275648', '060010001', '1', '意见领袖1（付费）', null, null, '人际', null, '意见领袖');
INSERT INTO `w_generalize_channel` VALUES ('106', '', '0', '1425275648', '060010002', '2', '意见领袖2（付费）', null, null, '人际', null, '意见领袖');
INSERT INTO `w_generalize_channel` VALUES ('107', '', '0', '1425275648', '060020001', '3', 'QQ群1（付费）', null, null, '人际', null, 'QQ群');
INSERT INTO `w_generalize_channel` VALUES ('108', '', '0', '1425275648', '060020002', '4', 'QQ群2（付费）', null, null, '人际', null, 'QQ群');
INSERT INTO `w_generalize_channel` VALUES ('109', '', '0', '1425275648', '070010001', '1', '理想股票论坛广告（付费）', null, null, '论坛', null, '论坛广告');
INSERT INTO `w_generalize_channel` VALUES ('11', '', '0', '1425275648', '010010011', '11', '炒股怎么赚钱（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('110', '', '0', '1425275648', '070010002', '2', 'MACD论坛广告（付费）', null, null, '论坛', null, '论坛广告');
INSERT INTO `w_generalize_channel` VALUES ('111', '', '0', '1425275648', '070020001', '3', '理想股票论坛合作帖（付费）', null, null, '论坛', null, '论坛合作帖');
INSERT INTO `w_generalize_channel` VALUES ('112', '', '0', '1425275648', '070020002', '4', 'MACD论坛合作帖（付费）', null, null, '论坛', null, '论坛合作帖');
INSERT INTO `w_generalize_channel` VALUES ('113', '', '0', '1425275648', '080010001', '1', '新浪广告（付费）', null, null, '门户网站', null, '门户广告');
INSERT INTO `w_generalize_channel` VALUES ('114', '', '0', '1425275648', '080010002', '2', '搜狐广告（付费）', null, null, '门户网站', null, '门户广告');
INSERT INTO `w_generalize_channel` VALUES ('115', '', '0', '1425275648', '080010003', '3', '腾讯广告（付费）', null, null, '门户网站', null, '门户广告');
INSERT INTO `w_generalize_channel` VALUES ('116', '', '0', '1425275648', '080020001', '4', '（付费）', null, null, '门户网站', null, '新闻（软文）');
INSERT INTO `w_generalize_channel` VALUES ('117', '', '0', '1425275648', '080030001', '5', '新浪专题页面（付费）', null, null, '门户网站', null, '专题页面');
INSERT INTO `w_generalize_channel` VALUES ('118', '', '0', '1425275648', '080030002', '6', '搜狐专题页面（付费）', null, null, '门户网站', null, '专题页面');
INSERT INTO `w_generalize_channel` VALUES ('119', '', '0', '1425275648', '080030003', '7', '腾讯专题页面（付费）', null, null, '门户网站', null, '专题页面');
INSERT INTO `w_generalize_channel` VALUES ('12', '', '0', '1425275648', '010010012', '12', '炒股有什么窍门（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('120', '', '0', '1425275648', '090010001', '1', '新浪微博官方号', null, null, '自媒体', null, '微博');
INSERT INTO `w_generalize_channel` VALUES ('121', '', '0', '1425275648', '090020001', '2', '微信服务号', null, null, '自媒体', null, '微信');
INSERT INTO `w_generalize_channel` VALUES ('122', '', '0', '1425275648', '090020002', '3', '微信订阅号', null, null, '自媒体', null, '微信');
INSERT INTO `w_generalize_channel` VALUES ('123', '', '0', '1425275648', null, '1', null, null, null, '其他', null, null);
INSERT INTO `w_generalize_channel` VALUES ('13', '', '0', '1425275648', '010010013', '13', '炒股钱不够怎么办（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('14', '', '0', '1425275648', '010010014', '14', '网上配资安全吗（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('15', '', '0', '1425275648', '010020001', '15', '兴趣定向（付费）', null, null, '百度', null, '百度网盟');
INSERT INTO `w_generalize_channel` VALUES ('16', '', '0', '1425275648', '010020002', '16', '网站定向（付费）', null, null, '百度', null, '百度网盟');
INSERT INTO `w_generalize_channel` VALUES ('17', '', '0', '1425275648', '010020003', '17', '关键词定向（付费）', null, null, '百度', null, '百度网盟');
INSERT INTO `w_generalize_channel` VALUES ('18', '', '0', '1425275648', '020010001', '1', '杠杆（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('19', '', '0', '1425275648', '020010002', '2', '荐股（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('2', '', '0', '1425275648', '010010002', '2', '什么是股票配资（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('20', '', '0', '1425275648', '020010003', '3', '平仓线（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('21', '', '0', '1425275648', '020010004', '4', '清仓（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('22', '', '0', '1425275648', '020010005', '5', '平仓（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('23', '', '0', '1425275648', '020010006', '6', '牛股推荐（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('24', '', '0', '1425275648', '020010007', '7', '股市行情（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('25', '', '0', '1425275648', '020010008', '8', '杠杆股（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('26', '', '0', '1425275648', '020010009', '9', '涨停（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('27', '', '0', '1425275648', '020010010', '10', '牛市（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('28', '', '0', '1425275648', '020010011', '11', '警戒线（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('29', '', '0', '1425275648', '020010012', '12', '免费炒股（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('3', '', '0', '1425275648', '010010003', '3', '配资是否合法（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('30', '', '0', '1425275648', '020010013', '13', '配资送钱（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('31', '', '0', '1425275648', '020010014', '14', '免费配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('32', '', '0', '1425275648', '020010015', '15', '配资不要钱（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('33', '', '0', '1425275648', '020010016', '16', '免费操盘（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('34', '', '0', '1425275648', '020010017', '17', '投资达人网（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('35', '', '0', '1425275648', '020010018', '18', '不差钱（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('36', '', '0', '1425275648', '020010019', '19', '米牛网（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('37', '', '0', '1425275648', '020010020', '20', '盈动力（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('38', '', '0', '1425275648', '020010021', '21', '贷未来（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('39', '', '0', '1425275648', '020010022', '22', '658金融（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('4', '', '0', '1425275648', '010010004', '4', '怎么配资（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('40', '', '0', '1425275648', '020010023', '23', '九牛网（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('41', '', '0', '1425275648', '020010024', '24', '股票配资网站（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('42', '', '0', '1425275648', '020010025', '25', '融资网站（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('43', '', '0', '1425275648', '020010026', '26', '配资公司（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('44', '', '0', '1425275648', '020010027', '27', '配资平台（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('45', '', '0', '1425275648', '020010028', '28', '配资网站（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('46', '', '0', '1425275648', '020010029', '29', '配资企业（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('47', '', '0', '1425275648', '020010030', '30', '股票配资公司（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('48', '', '0', '1425275648', '020010031', '31', '融资公司（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('49', '', '0', '1425275648', '020010032', '32', '配资网点（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('5', '', '0', '1425275648', '010010005', '5', '配资炒股利息高吗（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('50', '', '0', '1425275648', '020010033', '33', '配资网（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('51', '', '0', '1425275648', '020010034', '34', '配资利息（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('52', '', '0', '1425275648', '020010035', '35', '安全配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('53', '', '0', '1425275648', '020010036', '36', '融资融券（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('54', '', '0', '1425275648', '020010037', '37', '配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('55', '', '0', '1425275648', '020010038', '38', '融资炒股（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('56', '', '0', '1425275648', '020010039', '39', '短期配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('57', '', '0', '1425275648', '020010040', '40', '网上配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('58', '', '0', '1425275648', '020010041', '41', '炒股配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('59', '', '0', '1425275648', '020010042', '42', '借钱炒股（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('6', '', '0', '1425275648', '010010006', '6', '配资炒股的要求有哪些（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('60', '', '0', '1425275648', '020010043', '43', '配资流程（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('61', '', '0', '1425275648', '020010044', '44', '配资方法（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('62', '', '0', '1425275648', '020010045', '45', '股票配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('63', '', '0', '1425275648', '020010046', '46', '配资炒股（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('64', '', '0', '1425275648', '020010047', '47', '新手如何快速学会炒股（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('65', '', '0', '1425275648', '020010048', '48', '炒股怎么开户（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('66', '', '0', '1425275648', '020010049', '49', '炒股怎么赚钱（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('67', '', '0', '1425275648', '020010050', '50', '炒股有什么窍门（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('68', '', '0', '1425275648', '020010051', '51', '炒股钱不够怎么办（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('69', '', '0', '1425275648', '020010052', '52', '哪里有配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('7', '', '0', '1425275648', '010010007', '7', '股票配资是什么意思（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('70', '', '0', '1425275648', '020010053', '53', '配资网站哪家好（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('71', '', '0', '1425275648', '020010054', '54', '配资网排名前十是哪些（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('72', '', '0', '1425275648', '020010055', '55', '什么是配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('73', '', '0', '1425275648', '020010056', '56', '什么是股票配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('74', '', '0', '1425275648', '020010057', '57', '配资是否合法（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('75', '', '0', '1425275648', '020010058', '58', '怎么配资（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('76', '', '0', '1425275648', '020010059', '59', '配资炒股利息高吗（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('77', '', '0', '1425275648', '020010060', '60', '配资炒股的要求有哪些（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('78', '', '0', '1425275648', '020010061', '61', '股票配资是什么意思（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('79', '', '0', '1425275648', '020010062', '62', '配资杠杆是什么意思（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('8', '', '0', '1425275648', '010010008', '8', '配资杠杆是什么意思（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('80', '', '0', '1425275648', '020010063', '63', '股票配资可以有几倍杠杆（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('81', '', '0', '1425275648', '020010064', '64', '配资炒股哪家利息最低（付费）', null, null, '360', null, '360关键词');
INSERT INTO `w_generalize_channel` VALUES ('82', '', '0', '1425275648', '030010001', '1', '什么是配资（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('83', '', '0', '1425275648', '030010002', '2', '什么是股票配资（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('84', '', '0', '1425275648', '030010003', '3', '配资是否合法（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('85', '', '0', '1425275648', '030010004', '4', '怎么配资（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('86', '', '0', '1425275648', '030010005', '5', '配资炒股利息高吗（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('87', '', '0', '1425275648', '030010006', '6', '配资炒股的要求有哪些（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('88', '', '0', '1425275648', '030010007', '7', '股票配资是什么意思（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('89', '', '0', '1425275648', '030010008', '8', '配资杠杆是什么意思（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('9', '', '0', '1425275648', '010010009', '9', '股票配资可以有几倍杠杆（付费）', null, null, '百度', null, '百度关键词');
INSERT INTO `w_generalize_channel` VALUES ('90', '', '0', '1425275648', '030010009', '9', '股票配资可以有几倍杠杆（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('91', '', '0', '1425275648', '030010010', '10', '配资炒股哪家利息最低（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('92', '', '0', '1425275648', '030010011', '11', '网上配资安全吗（付费）', null, null, '搜狗', null, '搜狗关键词（付费）');
INSERT INTO `w_generalize_channel` VALUES ('93', '', '0', '1425275648', '030020001', '12', '兴趣定向（付费）', null, null, '搜狗', null, '搜狗网盟');
INSERT INTO `w_generalize_channel` VALUES ('94', '', '0', '1425275648', '030020002', '13', '网站定向（付费）', null, null, '搜狗', null, '搜狗网盟');
INSERT INTO `w_generalize_channel` VALUES ('95', '', '0', '1425275648', '030020003', '14', '关键词定向（付费）', null, null, '搜狗', null, '搜狗网盟');
INSERT INTO `w_generalize_channel` VALUES ('96', '', '0', '1425275648', '040010001', '1', '策略1（付费）', null, null, '品友DSP', null, '上海德搜');
INSERT INTO `w_generalize_channel` VALUES ('97', '', '0', '1425275648', '040010002', '2', '策略2（付费）', null, null, '品友DSP', null, '上海德搜');
INSERT INTO `w_generalize_channel` VALUES ('98', '', '0', '1425275648', '040010003', '3', '策略3（付费）', null, null, '品友DSP', null, '上海德搜');
INSERT INTO `w_generalize_channel` VALUES ('99', '', '0', '1425275648', '040020001', '4', '策略1（付费）', null, null, '品友DSP', null, '熊熊科技DSP');
