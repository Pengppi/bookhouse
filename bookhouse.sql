/*
Navicat MySQL Data Transfer

Source Server         : mysql_navicat
Source Server Version : 80032
Source Host           : localhost:3306
Source Database       : bookhouse

Target Server Type    : MYSQL
Target Server Version : 80032
File Encoding         : 65001

Date: 2023-03-08 21:31:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `book_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `book_name` varchar(40) NOT NULL,
  `book_author` varchar(80) DEFAULT NULL,
  `book_kind` tinyint DEFAULT NULL,
  `book_photo` varchar(80) DEFAULT NULL,
  `book_publisher` varchar(80) DEFAULT NULL,
  `book_version` varchar(80) DEFAULT NULL,
  `book_isbn` varchar(80) DEFAULT NULL,
  `book_quality` tinyint NOT NULL DEFAULT '2',
  `book_borrow` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('48', '10', '《朝花夕拾》', '鲁迅', '2', 'http://dummyimage.com/400x400', '清华大学', '第三版', '978-7-302-44068-8', '2', '0');
INSERT INTO `book` VALUES ('49', '10', '《计算机网络》', '内尔戴尔', '1', 'http://dummyimage.com/400x400', '机械工业', '第八版', '3-564-19034-4', '0', '1');
INSERT INTO `book` VALUES ('50', '10', '《数据库设计与原理》', '大卫奥哈拉论', '1', 'http://dummyimage.com/400x400', '人民邮电', '第三版', '978-3-564-19034-4', '1', '1');
INSERT INTO `book` VALUES ('51', '10', '《操作系统》', '兰德尔', '1', 'http://dummyimage.com/400x400', '清华大学', '第五版', '9-4345-2904-543', '1', '1');
INSERT INTO `book` VALUES ('52', '10', '《计算机组成原理》', '章邵辉', '1', null, '机械工业', '第四版', '3-534-2323-653', '2', '0');
INSERT INTO `book` VALUES ('53', '10', '《C语言程序设计》', '苏小红', '1', 'http://dummyimage.com/400x400', '电子工业', '第三版', '8-329-34434-04334-4', '2', '0');
INSERT INTO `book` VALUES ('54', '10', '《呐喊》', '鲁迅', '2', 'http://dummyimage.com/400x400', '清华大学', '微课版', '8-329-36836-4', '0', '1');
INSERT INTO `book` VALUES ('55', '10', '《啊Q正传》', '鲁迅', '2', 'http://dummyimage.com/400x400', '电子工业', '第五版', '978-7-232-98798-8', '2', '1');
INSERT INTO `book` VALUES ('57', '10', '《线性代数》', '迈尔斯', '1', 'http://dummyimage.com/400x400', '清华大学', '第八版', '7-434-43094-45', '0', '0');
INSERT INTO `book` VALUES ('59', '10', '《彷徨》', '鲁迅', '2', '', '机械工业', '第三版', '34-9239-34590-3', '0', '0');
INSERT INTO `book` VALUES ('66', '10', '《数据结构》', '王红梅', '1', '', '清华大学', '第3版', '45-25345-343-22', '1', '0');
INSERT INTO `book` VALUES ('67', '10', '《离散数学》', '马鹏程', '1', '', '机械工业', '第5版', '43-2323-23-232', '1', '0');

-- ----------------------------
-- Table structure for booktag
-- ----------------------------
DROP TABLE IF EXISTS `booktag`;
CREATE TABLE `booktag` (
  `book_isbn` varchar(80) NOT NULL,
  `book_kind` tinyint DEFAULT NULL,
  `education_sum` int NOT NULL DEFAULT '0',
  `literature_art_sum` int NOT NULL DEFAULT '0',
  `comic_humor_sum` int NOT NULL DEFAULT '0',
  `youth_sum` int NOT NULL DEFAULT '0',
  `children_sum` int NOT NULL DEFAULT '0',
  `social_science_sum` int NOT NULL DEFAULT '0',
  `life_sum` int NOT NULL DEFAULT '0',
  `technology_sum` int NOT NULL DEFAULT '0',
  `fiction_sum` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`book_isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of booktag
-- ----------------------------
INSERT INTO `booktag` VALUES ('3-534-2323-653', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('3-564-19034-4', '1', '2', '3', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('34-9239-34590-3', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('43-2323-23-232', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('45-25345-343-22', '1', '0', '1', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('7-434-43094-45', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('8-329-34434-04334-4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('8-329-36836-4', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('9-4345-2904-543', '1', '4', '1', '1', '0', '0', '0', '0', '1', '0');
INSERT INTO `booktag` VALUES ('978-3-564-19034-4', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('978-7-232-98798-8', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `booktag` VALUES ('978-7-302-44068-8', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `user_id` bigint NOT NULL,
  `borrow_date` datetime NOT NULL,
  `book_id` bigint NOT NULL,
  `should_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  `borrow_state` tinyint DEFAULT NULL,
  PRIMARY KEY (`user_id`,`borrow_date`,`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('10', '2023-01-03 23:55:27', '48', '2023-01-18 23:55:27', '2023-01-03 23:57:26', '1');
INSERT INTO `borrow` VALUES ('10', '2023-01-04 00:03:17', '49', '2023-01-19 00:03:17', '2023-01-04 00:04:25', '0');
INSERT INTO `borrow` VALUES ('10', '2023-01-04 00:07:21', '50', '2023-01-19 00:07:21', '2023-01-04 00:07:39', '1');
INSERT INTO `borrow` VALUES ('10', '2023-01-04 00:12:33', '51', '2023-01-19 00:12:33', '2023-01-04 00:13:32', '1');
INSERT INTO `borrow` VALUES ('10', '2023-01-04 10:39:41', '52', '2023-01-19 10:39:41', '2023-01-04 10:41:40', '0');
INSERT INTO `borrow` VALUES ('10', '2023-01-04 14:01:48', '53', '2023-01-19 14:01:48', '2023-01-04 14:05:11', '1');
INSERT INTO `borrow` VALUES ('10', '2023-01-05 22:42:16', '54', '2023-01-20 22:42:16', '2023-01-05 23:25:14', '1');
INSERT INTO `borrow` VALUES ('10', '2023-01-06 19:57:10', '55', '2023-01-21 19:57:10', '2023-01-06 20:01:39', '0');
INSERT INTO `borrow` VALUES ('10', '2023-02-20 12:55:47', '57', '2023-03-07 12:55:47', '2023-02-20 13:15:54', '1');
INSERT INTO `borrow` VALUES ('10', '2023-02-20 13:20:28', '57', '2023-03-07 13:20:28', '2023-02-20 13:27:33', '1');
INSERT INTO `borrow` VALUES ('10', '2023-02-21 10:46:30', '55', '2023-03-08 10:46:30', null, '0');
INSERT INTO `borrow` VALUES ('10', '2023-02-21 10:48:33', '59', '2023-03-08 10:48:33', null, '0');

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `sender_id` bigint NOT NULL,
  `receiver_id` bigint NOT NULL,
  `chat_time` datetime NOT NULL,
  `chat_type` tinyint NOT NULL,
  `chat_content` varchar(200) NOT NULL,
  PRIMARY KEY (`sender_id`,`receiver_id`,`chat_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of chat
-- ----------------------------
INSERT INTO `chat` VALUES ('3', '10', '2023-02-20 15:08:35', '0', '你好');
INSERT INTO `chat` VALUES ('3', '10', '2023-02-20 15:51:55', '0', '吃饭了没');
INSERT INTO `chat` VALUES ('3', '10', '2023-02-20 15:52:36', '0', '我也吃了');
INSERT INTO `chat` VALUES ('3', '10', '2023-02-20 15:52:58', '0', '想问你个事');
INSERT INTO `chat` VALUES ('3', '10', '2023-02-20 15:53:18', '0', '关于考研方面的');
INSERT INTO `chat` VALUES ('3', '10', '2023-02-20 16:11:56', '0', '你觉得三年工作经验和研究生学历哪个更重要？');
INSERT INTO `chat` VALUES ('3', '10', '2023-02-20 16:12:29', '0', '如果是你，你会怎么选？');
INSERT INTO `chat` VALUES ('4', '10', '2023-02-20 16:07:14', '0', '2');
INSERT INTO `chat` VALUES ('4', '10', '2023-02-20 16:07:31', '0', '3');
INSERT INTO `chat` VALUES ('4', '10', '2023-02-20 16:08:49', '0', '6');
INSERT INTO `chat` VALUES ('4', '10', '2023-02-20 16:09:21', '0', '8');
INSERT INTO `chat` VALUES ('10', '3', '2023-02-20 15:11:06', '0', 'hello');
INSERT INTO `chat` VALUES ('10', '3', '2023-02-20 15:52:26', '0', '吃了，你呢');
INSERT INTO `chat` VALUES ('10', '3', '2023-02-20 15:54:12', '0', '嗯，你说');
INSERT INTO `chat` VALUES ('10', '3', '2023-02-20 16:12:59', '0', '这个主要看你想走哪个方向吧');
INSERT INTO `chat` VALUES ('10', '3', '2023-02-20 16:13:20', '0', '如果你只是为了混个学历，那就没必要了');
INSERT INTO `chat` VALUES ('10', '3', '2023-02-20 21:02:45', '0', '但如果你是真想在这个学科上有所造诣，那当然能读就读');
INSERT INTO `chat` VALUES ('10', '3', '2023-02-20 21:04:13', '0', '我的话，还是会选择直接就业');
INSERT INTO `chat` VALUES ('10', '4', '2023-02-20 16:04:46', '0', '1');
INSERT INTO `chat` VALUES ('10', '4', '2023-02-20 16:07:57', '0', '4');
INSERT INTO `chat` VALUES ('10', '4', '2023-02-20 16:08:24', '0', '5');
INSERT INTO `chat` VALUES ('10', '4', '2023-02-20 16:09:05', '0', '7');
INSERT INTO `chat` VALUES ('10', '4', '2023-02-20 16:10:06', '0', '9');

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `code_id` char(6) NOT NULL,
  `book_id` bigint NOT NULL,
  `code_type` tinyint NOT NULL DEFAULT '0',
  `code_start` datetime NOT NULL,
  `code_end` datetime NOT NULL,
  `code_state` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`code_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of code
-- ----------------------------
INSERT INTO `code` VALUES ('126091', '55', '1', '2023-02-25 15:27:09', '2023-02-25 15:37:09', '1');
INSERT INTO `code` VALUES ('138163', '52', '0', '2023-02-25 10:51:31', '2023-02-25 11:01:31', '1');
INSERT INTO `code` VALUES ('279372', '57', '0', '2023-02-25 10:51:37', '2023-02-25 11:01:37', '1');
INSERT INTO `code` VALUES ('587440', '48', '0', '2023-02-25 15:24:46', '2023-02-25 15:34:46', '1');
INSERT INTO `code` VALUES ('670490', '66', '0', '2023-02-25 14:59:12', '2023-02-25 15:09:12', '1');

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `sender_id` bigint NOT NULL,
  `receiver_id` bigint NOT NULL,
  `unread_count` int DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`sender_id`,`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of contact
-- ----------------------------
INSERT INTO `contact` VALUES ('3', '3', '0', '2023-02-22 14:23:08');
INSERT INTO `contact` VALUES ('3', '10', '0', null);
INSERT INTO `contact` VALUES ('4', '10', '0', null);
INSERT INTO `contact` VALUES ('5', '10', '0', null);
INSERT INTO `contact` VALUES ('6', '10', '0', null);
INSERT INTO `contact` VALUES ('7', '10', '0', null);
INSERT INTO `contact` VALUES ('8', '10', '0', null);
INSERT INTO `contact` VALUES ('10', '3', '18', '2023-02-20 23:48:15');
INSERT INTO `contact` VALUES ('10', '4', '0', null);
INSERT INTO `contact` VALUES ('10', '5', '0', null);
INSERT INTO `contact` VALUES ('10', '6', '0', null);
INSERT INTO `contact` VALUES ('10', '7', '0', null);
INSERT INTO `contact` VALUES ('10', '8', '0', null);
INSERT INTO `contact` VALUES ('10', '9', '0', null);
INSERT INTO `contact` VALUES ('10', '10', '0', '2023-02-25 10:55:34');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(32) NOT NULL,
  `user_mail` varchar(30) DEFAULT NULL,
  `user_phone` varchar(80) DEFAULT NULL,
  `user_photo` varchar(80) DEFAULT NULL,
  `user_bookhouse` varchar(80) DEFAULT NULL,
  `user_address` varchar(80) DEFAULT NULL,
  `user_kind` tinyint DEFAULT '0',
  `user_longitude` decimal(20,15) DEFAULT NULL,
  `user_latitude` decimal(20,15) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3', 'hzf', '123456', '2399887711@qq.com', '13531435810', '94cbaab5-4750-4e8c-9335-91aa00a52d73.jpg', '百草园', '广东珠海', '0', '50.000000000000000', '50.000000000000000');
INSERT INTO `user` VALUES ('4', 'name4', '12345678', '4444444444@qq.com', null, 'name4_pic.jpeg', '三味书屋', '广东韶关', '0', '50.000000000000000', '50.000000000000000');
INSERT INTO `user` VALUES ('5', 'name5', '12345678', '5555555555@qq.com', null, 'name5_pic.jpeg', '鲁迅的小屋', '广东肇庆', '0', '50.000000000000000', '50.000000000000000');
INSERT INTO `user` VALUES ('6', 'llzzyy', '12345678', '2576820369@qq.com', null, 'llzzyy_pic.jpg', '动物园', '广东佛山', '0', '113.326073000000000', '23.116225000000000');
INSERT INTO `user` VALUES ('7', 'lzy123', '12345678', '111111111@qq.com', null, 'lzy123_pic.jpg', '皇家牢房', '广东江门', '0', '50.000000000000000', '50.000000000000000');
INSERT INTO `user` VALUES ('8', 'lzy123', '12345678', '2222222222@qq.com', null, 'lzy123_pic2.jpeg', '小黑屋', '广东中山', '0', '50.000000000000000', '50.000000000000000');
INSERT INTO `user` VALUES ('10', 'name3', '12345678', '3333333333@qq.com', null, 'e0db79a54063.jpg', '饮冰室', '广东阳江', '0', '50.000000000000000', '50.000000000000000');
