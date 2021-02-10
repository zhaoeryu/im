/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : im

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 17/06/2020 14:17:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apply_audit
-- ----------------------------
DROP TABLE IF EXISTS `apply_audit`;
CREATE TABLE `apply_audit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请类型：friend:添加好友申请，group:添加群申请',
  `apply_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请者ID',
  `audit_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审核者ID',
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请类型为friend，则值为分组ID；申请类型为group，则值为群ID',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请备注',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '申请时间',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '审核状态：0.已申请，1.已同意，2.已拒绝',
  `read_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读 0：未读，1：已读',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '请求申请表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apply_audit
-- ----------------------------
INSERT INTO `apply_audit` VALUES (1, 'friend', '1', '4', '2', '你好阿', '2020-06-14 16:17:29', 0, 0, '2020-06-16 22:04:49');
INSERT INTO `apply_audit` VALUES (2, 'group', '1', '2', '3', '我想拼车阿', '2020-06-14 16:22:07', 1, 1, '2020-06-16 22:41:35');
INSERT INTO `apply_audit` VALUES (5, 'friend', '1272898041567158274', '2', '1', '来吧', '2020-06-16 10:29:02', 1, 1, '2020-06-16 22:41:36');
INSERT INTO `apply_audit` VALUES (6, 'friend', '1', '1272898041567158274', '1', '来吧？？', '2020-06-16 10:29:23', 0, 0, '2020-06-16 22:25:39');
INSERT INTO `apply_audit` VALUES (7, 'friend', '2', '4', '1', '你看起来少点什么', '2020-06-16 22:17:32', 0, 0, '2020-06-16 22:17:31');
INSERT INTO `apply_audit` VALUES (8, 'group', '2', '3', '4', '我想入坑', '2020-06-17 13:53:26', 2, 1, '2020-06-17 13:59:18');

-- ----------------------------
-- Table structure for column_config
-- ----------------------------
DROP TABLE IF EXISTS `column_config`;
CREATE TABLE `column_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `column_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `column_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `form_show` bit(1) NULL DEFAULT NULL,
  `query_show` bit(1) NULL DEFAULT NULL,
  `form_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `key_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `list_show` bit(1) NULL DEFAULT NULL,
  `not_null` bit(1) NULL DEFAULT NULL,
  `query_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成字段信息存储' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for gen_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_config`;
CREATE TABLE `gen_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `cover` bit(1) NULL DEFAULT NULL COMMENT '是否覆盖',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `pack` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '至于哪个包下',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端代码生成的路径',
  `api_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端Api文件路径',
  `api_alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口描述',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `class_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体类名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成器配置' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for message_log
-- ----------------------------
DROP TABLE IF EXISTS `message_log`;
CREATE TABLE `message_log`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `from_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息发送者ID',
  `to_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息接收者ID，如果为群聊消息，则为群ID',
  `chat_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型，单聊friend/群聊group',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容，json格式，不同消息类型，json格式不一样',
  `send_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '消息发送时间',
  `read_flag` bit(1) NULL DEFAULT b'1' COMMENT '是否已读（单聊可用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_log
-- ----------------------------
INSERT INTO `message_log` VALUES ('1273062450977468417', '1', '2', 'friend', '{\"type\":1,\"read\":false,\"msg\":\"看个小视频放松一下video[http://cdn.layui.com/xxx/a.avi]\"}', '2020-06-17 09:18:35', b'1');
INSERT INTO `message_log` VALUES ('1273062700463058946', '1', '2', 'friend', '{\"type\":1,\"read\":false,\"msg\":\"video[http://cdn.layui.com/xxx/a.avi]\"}', '2020-06-17 09:19:35', b'1');
INSERT INTO `message_log` VALUES ('1273064287994572802', '1', '2', 'friend', '{\"type\":1,\"msg\":\"阿斯蒂芬\"}', '2020-06-17 09:25:53', b'0');
INSERT INTO `message_log` VALUES ('1273064293405224961', '1', '2', 'friend', '{\"type\":1,\"msg\":\"你哪年\"}', '2020-06-17 09:25:55', b'0');
INSERT INTO `message_log` VALUES ('1273065213945876481', '1', '2', 'friend', '{\"type\":1,\"msg\":\"阿斯弗\"}', '2020-06-17 09:29:33', b'0');
INSERT INTO `message_log` VALUES ('1273075042265518081', '1', '2', 'friend', '{\"type\":1,\"msg\":\"你好啊\"}', '2020-06-17 10:08:37', b'1');
INSERT INTO `message_log` VALUES ('1273078773451214849', '2', '1', 'friend', '{\"type\":1,\"msg\":\"我是阿龙\"}', '2020-06-17 10:23:26', b'1');
INSERT INTO `message_log` VALUES ('1273078808926638082', '1', '2', 'friend', '{\"type\":1,\"msg\":\"我是二宇\"}', '2020-06-17 10:23:35', b'1');
INSERT INTO `message_log` VALUES ('1273081736651513857', '1', '1', 'group', '{\"type\":1,\"msg\":\"老铁，开车吗\"}', '2020-06-17 10:35:13', b'1');
INSERT INTO `message_log` VALUES ('1273081875743023106', '2', '1', 'group', '{\"type\":1,\"msg\":\"开车\"}', '2020-06-17 10:35:47', b'1');
INSERT INTO `message_log` VALUES ('1273082134145703937', '3', '1', 'group', '{\"type\":1,\"msg\":\"老了老弟\"}', '2020-06-17 10:36:48', b'1');
INSERT INTO `message_log` VALUES ('1273087251938971650', '1', '1', 'group', '{\"type\":1,\"msg\":\"走上车\"}', '2020-06-17 10:57:08', b'1');
INSERT INTO `message_log` VALUES ('1273087332364750850', '2', '1', 'group', '{\"type\":1,\"msg\":\"我要打辅助\"}', '2020-06-17 10:57:28', b'1');
INSERT INTO `message_log` VALUES ('1273087449335500801', '3', '1', 'group', '{\"type\":1,\"msg\":\"我要打上单\"}', '2020-06-17 10:57:55', b'1');
INSERT INTO `message_log` VALUES ('1273090748520509442', '3', '1', 'group', '{\"type\":1,\"msg\":\"有人仔阿妈\"}', '2020-06-17 11:11:01', b'1');
INSERT INTO `message_log` VALUES ('1273091029677244418', '1', '1', 'group', '{\"type\":1,\"msg\":\"啥也不是\"}', '2020-06-17 11:12:08', b'1');
INSERT INTO `message_log` VALUES ('1273091105292156929', '3', '1', 'group', '{\"type\":1,\"msg\":\"哈哈\"}', '2020-06-17 11:12:27', b'1');
INSERT INTO `message_log` VALUES ('1273091135860244481', '2', '1', 'group', '{\"type\":1,\"msg\":\"我也来一句\"}', '2020-06-17 11:12:34', b'1');
INSERT INTO `message_log` VALUES ('1273091377347297281', '2', '3', 'group', '{\"type\":1,\"msg\":\"雷子看不到\"}', '2020-06-17 11:13:32', b'1');
INSERT INTO `message_log` VALUES ('1273091529684418562', '1', '3', 'group', '{\"type\":1,\"msg\":\"哈哈\"}', '2020-06-17 11:14:08', b'1');
INSERT INTO `message_log` VALUES ('1273093185037824002', '3', '1', 'group', '{\"type\":1,\"msg\":\"face[晕] \"}', '2020-06-17 11:20:42', b'1');
INSERT INTO `message_log` VALUES ('1273093223306653698', '3', '1', 'group', '{\"type\":1,\"msg\":\"img[]\"}', '2020-06-17 11:20:52', b'1');
INSERT INTO `message_log` VALUES ('1273093244072652802', '3', '1', 'group', '{\"type\":1,\"msg\":\"img[]\"}', '2020-06-17 11:20:57', b'1');
INSERT INTO `message_log` VALUES ('1273093260870840321', '3', '1', 'group', '{\"type\":1,\"msg\":\"file()[下载文件]\"}', '2020-06-17 11:21:01', b'1');
INSERT INTO `message_log` VALUES ('1273093290973360130', '3', '1', 'group', '{\"type\":1,\"msg\":\"[pre class=layui-code]阿斯蒂芬[/pre]\"}', '2020-06-17 11:21:08', b'1');
INSERT INTO `message_log` VALUES ('1273093336510918657', '3', '1', 'friend', '{\"type\":1,\"msg\":\"sd发\"}', '2020-06-17 11:21:19', b'0');
INSERT INTO `message_log` VALUES ('1273093353959223298', '3', '1', 'friend', '{\"type\":1,\"msg\":\"face[怒] \"}', '2020-06-17 11:21:23', b'0');
INSERT INTO `message_log` VALUES ('1273093453150318593', '1', '2', 'friend', '{\"type\":1,\"msg\":\"士大夫\"}', '2020-06-17 11:21:47', b'1');
INSERT INTO `message_log` VALUES ('1273093486876717058', '2', '1', 'group', '{\"type\":1,\"msg\":\"士大夫\"}', '2020-06-17 11:21:55', b'1');
INSERT INTO `message_log` VALUES ('1273094143348219906', '2', '1', 'group', '{\"type\":1,\"msg\":\"没人在吧\"}', '2020-06-17 11:24:31', b'1');
INSERT INTO `message_log` VALUES ('1273094411607494658', '2', '1', 'group', '{\"type\":1,\"msg\":\"没人在\"}', '2020-06-17 11:25:35', b'1');
INSERT INTO `message_log` VALUES ('1273094491261521921', '1', '1', 'group', '{\"type\":1,\"msg\":\"我来了\"}', '2020-06-17 11:25:54', b'1');
INSERT INTO `message_log` VALUES ('1273094549197443073', '3', '1', 'group', '{\"type\":1,\"msg\":\"我也来了\"}', '2020-06-17 11:26:08', b'1');
INSERT INTO `message_log` VALUES ('1273101771071758338', '1', '1', 'group', '{\"type\":1,\"msg\":\"asdf\"}', '2020-06-17 11:54:49', b'1');
INSERT INTO `message_log` VALUES ('1273110009573969922', '1', '2', 'friend', '{\"type\":1,\"msg\":\"安抚\"}', '2020-06-17 12:27:34', b'0');
INSERT INTO `message_log` VALUES ('1273110011591430146', '1', '2', 'friend', '{\"type\":1,\"msg\":\"a\"}', '2020-06-17 12:27:35', b'0');
INSERT INTO `message_log` VALUES ('1273110014221258753', '1', '2', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 12:27:35', b'0');
INSERT INTO `message_log` VALUES ('1273110025579433986', '1', '2', 'friend', '{\"type\":1,\"msg\":\"bcd\"}', '2020-06-17 12:27:38', b'0');
INSERT INTO `message_log` VALUES ('1273110028997791746', '1', '2', 'friend', '{\"type\":1,\"msg\":\"s\"}', '2020-06-17 12:27:39', b'0');
INSERT INTO `message_log` VALUES ('1273110032743305217', '1', '2', 'friend', '{\"type\":1,\"msg\":\"d\"}', '2020-06-17 12:27:40', b'0');
INSERT INTO `message_log` VALUES ('1273110035276664833', '1', '2', 'friend', '{\"type\":1,\"msg\":\"3\"}', '2020-06-17 12:27:40', b'0');
INSERT INTO `message_log` VALUES ('1273110041836556289', '1', '2', 'friend', '{\"type\":1,\"msg\":\"ac\"}', '2020-06-17 12:27:42', b'0');
INSERT INTO `message_log` VALUES ('1273110054847287298', '1', '2', 'friend', '{\"type\":1,\"msg\":\"测试a\"}', '2020-06-17 12:27:45', b'0');
INSERT INTO `message_log` VALUES ('1273110061159714817', '1', '2', 'friend', '{\"type\":1,\"msg\":\"啊啊啊\"}', '2020-06-17 12:27:46', b'0');
INSERT INTO `message_log` VALUES ('1273110066943660034', '1', '2', 'friend', '{\"type\":1,\"msg\":\"顶顶顶\"}', '2020-06-17 12:27:48', b'0');
INSERT INTO `message_log` VALUES ('1273110073549688834', '1', '2', 'friend', '{\"type\":1,\"msg\":\"方法\"}', '2020-06-17 12:27:49', b'0');
INSERT INTO `message_log` VALUES ('1273110078100508673', '1', '2', 'friend', '{\"type\":1,\"msg\":\"22\"}', '2020-06-17 12:27:51', b'0');
INSERT INTO `message_log` VALUES ('1273114139726090242', '1', '1', 'group', '{\"type\":1,\"msg\":\"阿斯蒂\"}', '2020-06-17 12:43:59', b'1');
INSERT INTO `message_log` VALUES ('1273130979508523009', '2', '1', 'friend', '{\"type\":1,\"msg\":\"你用移动端的吗？\"}', '2020-06-17 13:50:54', b'0');
INSERT INTO `message_log` VALUES ('1273132390765989890', '1', '2', 'friend', '{\"type\":1,\"msg\":\"士大夫\"}', '2020-06-17 13:56:30', b'1');
INSERT INTO `message_log` VALUES ('1273132411125141506', '2', '1', 'friend', '{\"type\":1,\"msg\":\"士大夫\"}', '2020-06-17 13:56:35', b'1');
INSERT INTO `message_log` VALUES ('1273132442037161985', '2', '1', 'friend', '{\"type\":1,\"msg\":\"士大夫\"}', '2020-06-17 13:56:42', b'1');
INSERT INTO `message_log` VALUES ('1273132454372610050', '2', '1', 'friend', '{\"type\":1,\"msg\":\"士大夫\"}', '2020-06-17 13:56:45', b'1');
INSERT INTO `message_log` VALUES ('1273132465625927682', '2', '1', 'friend', '{\"type\":1,\"msg\":\"士大夫\"}', '2020-06-17 13:56:48', b'1');
INSERT INTO `message_log` VALUES ('1273132475679674369', '2', '1', 'friend', '{\"type\":1,\"msg\":\"啊啊\"}', '2020-06-17 13:56:51', b'1');
INSERT INTO `message_log` VALUES ('1273132785810706434', '3', '1', 'friend', '{\"type\":1,\"msg\":\"你在用手机端吗？face[阴险] \"}', '2020-06-17 13:58:04', b'1');
INSERT INTO `message_log` VALUES ('1273132850218438657', '1', '3', 'friend', '{\"type\":1,\"msg\":\"face[微笑] 是啊\"}', '2020-06-17 13:58:20', b'1');
INSERT INTO `message_log` VALUES ('1273132889451958274', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:29', b'1');
INSERT INTO `message_log` VALUES ('1273132891016433666', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:30', b'1');
INSERT INTO `message_log` VALUES ('1273132892387971074', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:30', b'1');
INSERT INTO `message_log` VALUES ('1273132893348466690', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:30', b'1');
INSERT INTO `message_log` VALUES ('1273132894082469889', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:30', b'1');
INSERT INTO `message_log` VALUES ('1273132894720004097', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:30', b'1');
INSERT INTO `message_log` VALUES ('1273132896322228225', '1', '3', 'friend', '{\"type\":1,\"msg\":\"11\"}', '2020-06-17 13:58:31', b'1');
INSERT INTO `message_log` VALUES ('1273132896896847874', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:31', b'1');
INSERT INTO `message_log` VALUES ('1273132897454690306', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:31', b'1');
INSERT INTO `message_log` VALUES ('1273132898033504257', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:31', b'1');
INSERT INTO `message_log` VALUES ('1273132898608123905', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:31', b'1');
INSERT INTO `message_log` VALUES ('1273132899149189122', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:31', b'1');
INSERT INTO `message_log` VALUES ('1273132899753168897', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:32', b'1');
INSERT INTO `message_log` VALUES ('1273132900382314498', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:32', b'1');
INSERT INTO `message_log` VALUES ('1273132901967761410', '1', '3', 'friend', '{\"type\":1,\"msg\":\"11\"}', '2020-06-17 13:58:32', b'1');
INSERT INTO `message_log` VALUES ('1273132902538186754', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:32', b'1');
INSERT INTO `message_log` VALUES ('1273132903146360834', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:32', b'1');
INSERT INTO `message_log` VALUES ('1273132903595151362', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:33', b'1');
INSERT INTO `message_log` VALUES ('1273132904194936834', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:33', b'1');
INSERT INTO `message_log` VALUES ('1273132904845053953', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:33', b'1');
INSERT INTO `message_log` VALUES ('1273132907114172417', '1', '3', 'friend', '{\"type\":1,\"msg\":\"111\"}', '2020-06-17 13:58:33', b'1');
INSERT INTO `message_log` VALUES ('1273132907688792066', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:34', b'1');
INSERT INTO `message_log` VALUES ('1273132908275994626', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:34', b'1');
INSERT INTO `message_log` VALUES ('1273132908833837058', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:34', b'1');
INSERT INTO `message_log` VALUES ('1273132909416845313', '1', '3', 'friend', '{\"type\":1,\"msg\":\"1\"}', '2020-06-17 13:58:34', b'1');
INSERT INTO `message_log` VALUES ('1273132913070084098', '1', '3', 'friend', '{\"type\":1,\"msg\":\"11\"}', '2020-06-17 13:58:35', b'1');
INSERT INTO `message_log` VALUES ('1273133190615568385', '1', '3', 'friend', '{\"type\":1,\"msg\":\"[pre class=layui-code]123[/pre]\"}', '2020-06-17 13:59:41', b'1');
INSERT INTO `message_log` VALUES ('1273137114512789505', '3', '1', 'group', '{\"type\":1,\"msg\":\"移动端没有群吗\"}', '2020-06-17 14:15:16', b'1');
INSERT INTO `message_log` VALUES ('1273137169353314305', '1', '1', 'group', '{\"type\":1,\"msg\":\"找不到入口\"}', '2020-06-17 14:15:30', b'1');
INSERT INTO `message_log` VALUES ('1273137242611027970', '2', '1', 'group', '{\"type\":1,\"msg\":\"尴尬了\"}', '2020-06-17 14:15:47', b'1');

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('阿龙', '/rPgVGLL1vbyZb0+jBsCVw==', 'bhZWqqzcibnRLjHJ/TgHKQ==', '2020-06-16 13:59:26');
INSERT INTO `persistent_logins` VALUES ('阿龙', '3e5GzvlSNGF9pCT0KZXGmQ==', '3ms/8QjHfhBVJJ4ozcebiw==', '2020-06-16 22:41:39');
INSERT INTO `persistent_logins` VALUES ('阿龙', '5TyqIvYKKuucWTE9OA8S4Q==', 'VDVxpKop88z6myBMn596Cg==', '2020-06-16 13:31:03');
INSERT INTO `persistent_logins` VALUES ('阿龙', '6a6Pq1y1Tny8MB0t5hfq4w==', 'pKViUW9y0mdIOGmdkjjDKg==', '2020-06-16 22:29:30');
INSERT INTO `persistent_logins` VALUES ('阿龙', '6Z8xDRGl896A6/UMs3o8jA==', 'L4gDp0XoTKDSVYRWxzyDAQ==', '2020-06-17 13:50:44');
INSERT INTO `persistent_logins` VALUES ('阿龙', '8pGDEc2KTWaQkevKP71WJA==', '3mvst9gzBGh8KOdLoGcgqw==', '2020-06-16 13:38:26');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'a4wt5cMw3uhJRPyBfpgEqA==', '8VwCZEXbSaM2z8K72ljJcA==', '2020-06-17 10:35:19');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'dE/+SwlNgA8UxRLTapeAAw==', 'oK+LBLlub0S7gsZDD0A89A==', '2020-06-16 13:42:40');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'EdB3uaF2wbrXgfne3sJn9g==', '7G2Kf5zAG950KLgbtTm7Mw==', '2020-06-16 22:30:59');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'eGxBMqqS1qeuo3bOOiYVUQ==', '3QxsqLnZ9b2HXOB9MI+iOw==', '2020-06-17 10:48:23');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'ePuc0KvyQ/QJKcZnjEfekg==', 'tLJ4o1Mq/lDhT4zkFDM3VA==', '2020-06-16 22:40:02');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'ffAOx7H4YaGhQxOvFtKsIA==', 'cP5B1EVU9473mM2rTuCMhA==', '2020-06-16 22:16:01');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'FFIVYQwB0zXWfI2rRrJFOQ==', 'YUvdlQLKG8cPte6u0QniIg==', '2020-06-16 13:38:18');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'fjsHfTrkdVotTi77AYjX0g==', 'yigyefrptOnzStvADzLqEA==', '2020-06-16 13:46:09');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'fTC1Nv2UDbfBrDhbJZjO/g==', '3dJpfTRhmuUlWnzSyvAxtg==', '2020-06-17 10:29:55');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'gGqNJijFGOkZuf7wYLlK0g==', 'snAamzZZu+wMixHFl+ej/A==', '2020-06-16 22:14:54');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'HdccbeoAwC2Foqah/oRxuA==', 'nm6QwYZ4JmCeIak+h+ccCQ==', '2020-06-16 22:29:12');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'hZNs8s+3Hy1mAn6zZewSwQ==', 'TUXv2VZPR2hqGMPXJuCvBg==', '2020-06-16 22:12:34');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'i/9FkvP9Q9kM671tAlR7pQ==', 'mqy7Gtqen1PFppdMNdK+FQ==', '2020-06-16 22:10:58');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'JCBQhMHzAP9dMC8jiL9sow==', 'N05CEAu1+NBexBSTQk/kWw==', '2020-06-17 10:22:59');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'JSBjOif7AVh6XoKx2tSrAA==', 'Om+9+UcOiOZMryvV9n7SJw==', '2020-06-16 14:08:09');
INSERT INTO `persistent_logins` VALUES ('雷子', 'KDON68gBQk5kLyI/PYm9Yw==', 'McLTP9f+FENDOR2H9yQA4w==', '2020-06-17 13:57:44');
INSERT INTO `persistent_logins` VALUES ('aa', 'KvLhIkyFmGJezDag30yVSw==', '1QOqBfAZnpfDfhQD2G2v/Q==', '2020-06-16 22:25:25');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'lenQS8SEfmxuib+FyQwQaQ==', 'UKybCE3UqnCWOGruIFrm4w==', '2020-06-16 14:03:41');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'LONBY+83kq94OuI2MEtrGg==', '2FWnWzFs0eBuo/0RO+a1bw==', '2020-06-16 14:01:05');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'MQ9qiQFmFZgYeV38Bg3fAA==', 'Y/Tnr9pmAuOpcn/sWBt5EA==', '2020-06-16 13:04:37');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'o9WecPA/vKizrHACbhtUtg==', 'tV7xW8u1vbEaqridBQ2rWw==', '2020-06-16 13:29:22');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'oDGT2CPPC97+IKcZoI0wsA==', 'KBhRlvPTQ5NV2doP7QuLlQ==', '2020-06-16 14:08:05');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'ODzuzvAVo7GIvkiIDo7phg==', 'G9HjxBHGdgTE2VR+p/jN5g==', '2020-06-16 10:27:25');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'oEY7T/xCVe49m9Lz+M59/w==', 'Gr75y+t2if5EWqKGbIc50Q==', '2020-06-16 14:09:03');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'OqSm3/FviKQYVTDMoKspIA==', 'sgIQqCVK/t8uASpua8neiQ==', '2020-06-16 10:23:00');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'p8fln0+kWFbReVWPneflUw==', 'o4tXtjnaaq7LBNGHwG4feA==', '2020-06-16 13:37:45');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'PLUaKEDs65crAJi14dEyuw==', 'bjuDABFySKGG6zz1SRq7Bg==', '2020-06-16 22:32:06');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'pt3d95uGNQ9z+wOM8N+rNQ==', 'zMOQOxUtYo/s9UUFS2ZaLQ==', '2020-06-16 13:33:46');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'q+NmF0DEmtPX81o73WJyrA==', 'rxeHzmxH2U8p7R4a1R8hdg==', '2020-06-17 10:40:47');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'QIHRXvjBoRh/o0FBgeu3GA==', '7C3MMqVdjgfFBzbQVBGvnA==', '2020-06-16 22:16:20');
INSERT INTO `persistent_logins` VALUES ('zhaoey', 'QyMqd/ng7cn5tKlPv5k9Sg==', 'gka5kp7WRX2r0cTIHFgSbg==', '2020-06-17 13:31:51');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'r68Ca946jiP9t51IXhrmaQ==', 'B6yMt7iL+cf6MjgRstaKCA==', '2020-06-16 22:40:52');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'rr+hY8otZouOfMgggVT6zw==', 'g5Gvv+9GFA2a6Bbxs2tX5A==', '2020-06-16 13:03:44');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'RrJE2oFr0Thq07SQe7HE1A==', 'cRIqGdJxNPdKiC45vvoOMw==', '2020-06-16 22:15:33');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'SPVqEK1vSJo7tn1dC2k2TQ==', 'Le4tR2nGqBcc3tqEnRBPcw==', '2020-06-17 10:57:19');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'sqUxfqrYwfGDv6LOQq9Plw==', 'NnLU8GwnbK/IjRtET71zYw==', '2020-06-17 11:12:12');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'TLXxHmAs4BpevVTotEQAig==', 'oWtLXg3STjXA4hTdn2Puog==', '2020-06-16 22:15:14');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'USWeIIlG0BHlzWb6P9Rz8A==', 'y6kODnS0EDSF8qBmSw4nCA==', '2020-06-17 10:11:43');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'vC3TfItSC81x1tJ91G00tQ==', 'fy/OrLH7CJOpbqI5P3sIpw==', '2020-06-16 22:40:35');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'vXTLna7BdErqgMV7eff/aw==', 'EGMHS5MUq/hgDYiS0Slupw==', '2020-06-16 13:32:12');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'w7AGm2DxgCIpv625ajB7Ug==', 'Qb83qSXBT2XizQZpLX5mAQ==', '2020-06-16 22:41:26');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'WQXe1skO47ELCjiVNpts+w==', '7JilOJlkLboNIWHIHmGnkg==', '2020-06-16 22:41:15');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'wVd4BVr4ARa9AQVgNe6pyg==', '0mpSC/kIwwwKP96jaXIR7A==', '2020-06-17 10:23:14');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'WVen8LLonNMoNuvgki3gMw==', 'EaM2l6Gi65nJ7VzwISqQNw==', '2020-06-16 13:03:52');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'xFCc8CLtyBY+ip/6+MEwZQ==', 'zg6kvgP/ivuKi5jm4eLr3Q==', '2020-06-16 14:07:31');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'Xx4DF55Nhg1IckQO6+FozA==', 'ES3Z8whKxSorQa3o4jSaxQ==', '2020-06-16 13:02:51');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'z8l9J+l6UwZJS0hVzGem/g==', 'anRPhJLP+o/hTA8Zxtw4sQ==', '2020-06-16 22:40:56');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'zdm+12AS5ZdIKW3//N35lg==', '0U+rbTVZoP0uhcAcTchLzQ==', '2020-06-17 10:08:15');
INSERT INTO `persistent_logins` VALUES ('阿龙', 'znoiH8KDNqJW4ICsb1sy0g==', '8EHSu2jrn14zW8NTDQ/38A==', '2020-06-16 22:25:51');

-- ----------------------------
-- Table structure for rel_user_friend_group
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_friend_group`;
CREATE TABLE `rel_user_friend_group`  (
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组ID',
  `friend_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '处于分组中的好友ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户的好友和分组的关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rel_user_friend_group
-- ----------------------------
INSERT INTO `rel_user_friend_group` VALUES ('1', '1', '2');
INSERT INTO `rel_user_friend_group` VALUES ('1', '2', '3');
INSERT INTO `rel_user_friend_group` VALUES ('2', '1', '1');
INSERT INTO `rel_user_friend_group` VALUES ('2', '1', '3');
INSERT INTO `rel_user_friend_group` VALUES ('3', '1', '1');
INSERT INTO `rel_user_friend_group` VALUES ('3', '1', '2');
INSERT INTO `rel_user_friend_group` VALUES ('2', '1', '1272898041567158274');
INSERT INTO `rel_user_friend_group` VALUES ('1272898041567158274', '1', '2');

-- ----------------------------
-- Table structure for rel_user_group
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_group`;
CREATE TABLE `rel_user_group`  (
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群ID',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成员ID',
  `last_ack_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次查看消息的时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和群的关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rel_user_group
-- ----------------------------
INSERT INTO `rel_user_group` VALUES ('1', '1', '2020-06-16 23:15:28');
INSERT INTO `rel_user_group` VALUES ('1', '2', '2020-06-16 23:15:28');
INSERT INTO `rel_user_group` VALUES ('1', '3', '2020-06-16 23:15:28');
INSERT INTO `rel_user_group` VALUES ('2', '1', '2020-06-16 23:15:28');
INSERT INTO `rel_user_group` VALUES ('2', '2', '2020-06-16 23:15:28');
INSERT INTO `rel_user_group` VALUES ('3', '2', '2020-06-16 23:15:28');
INSERT INTO `rel_user_group` VALUES ('4', '3', '2020-06-16 23:15:28');
INSERT INTO `rel_user_group` VALUES ('3', '1', '2020-06-16 23:15:28');

-- ----------------------------
-- Table structure for user_friend_group
-- ----------------------------
DROP TABLE IF EXISTS `user_friend_group`;
CREATE TABLE `user_friend_group`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `groupname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户分组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_friend_group
-- ----------------------------
INSERT INTO `user_friend_group` VALUES ('1', '我的好友');
INSERT INTO `user_friend_group` VALUES ('2', '工作');

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `groupname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群头像',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群主ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户群' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES ('1', '五黑群', 'http://tvax1.sinaimg.cn/crop.0.0.300.300.180/006Iv8Wjly8ff7ghbigcij308c08ct8i.jpg', '1');
INSERT INTO `user_group` VALUES ('2', '编码群', 'http://tp1.sinaimg.cn/1571889140/180/40030060651/1', '1');
INSERT INTO `user_group` VALUES ('3', '拼车群', 'http://tp2.sinaimg.cn/2211874245/180/40050524279/0', '2');
INSERT INTO `user_group` VALUES ('4', '前端群', 'http://tp2.sinaimg.cn/5488749285/50/5719808192/1', '3');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '在线状态',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'zhaoey', 'online', '熬夜打码', 'http://tvax1.sinaimg.cn/crop.0.0.300.300.180/006Iv8Wjly8ff7ghbigcij308c08ct8i.jpg');
INSERT INTO `user_info` VALUES ('1272898041567158274', 'aa', 'online', '认真学习', 'http://tvax1.sinaimg.cn/crop.0.0.300.300.180/006Iv8Wjly8ff7ghbigcij308c08ct8i.jpg');
INSERT INTO `user_info` VALUES ('2', '阿龙', 'online', '让天下没有难写的代码', 'http://tp4.sinaimg.cn/2145291155/180/5601307179/1');
INSERT INTO `user_info` VALUES ('3', '雷子', 'online', '代码在囧途，也要写到底', 'http://tp2.sinaimg.cn/1783286485/180/5677568891/1');
INSERT INTO `user_info` VALUES ('4', 'Z_子晴', 'online', '微电商达人', 'http://tva3.sinaimg.cn/crop.0.0.512.512.180/8693225ajw8f2rt20ptykj20e80e8weu.jpg');
INSERT INTO `user_info` VALUES ('5', '罗小凤', 'online', '在自己实力不济的时候，不要去相信什么媒体和记者。他们不是善良的人，有时候候他们的采访对当事人而言就是陷阱', 'http://tp1.sinaimg.cn/1241679004/180/5743814375/0');

SET FOREIGN_KEY_CHECKS = 1;
