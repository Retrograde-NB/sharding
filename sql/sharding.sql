/*
 Navicat Premium Dump SQL

 Source Server         : localhost - MySQL
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : localhost:3306
 Source Schema         : sharding

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 17/12/2024 10:44:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for incorder_0
-- ----------------------------
DROP TABLE IF EXISTS `incorder_0`;
CREATE TABLE `incorder_0`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for incorder_1
-- ----------------------------
DROP TABLE IF EXISTS `incorder_1`;
CREATE TABLE `incorder_1`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for maxid
-- ----------------------------
DROP TABLE IF EXISTS `maxid`;
CREATE TABLE `maxid`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nextid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for strorder_0
-- ----------------------------
DROP TABLE IF EXISTS `strorder_0`;
CREATE TABLE `strorder_0`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `userid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for strorder_1
-- ----------------------------
DROP TABLE IF EXISTS `strorder_1`;
CREATE TABLE `strorder_1`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `userid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Function structure for getid
-- ----------------------------
DROP FUNCTION IF EXISTS `getid`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getid`(table_name VARCHAR(50)) RETURNS bigint(20)
BEGIN
		-- 定义变量
		DECLARE id BIGINT(20);
		-- 给定义的变量赋值
		update maxid set nextid=nextid+1 where name = table_name;
		SELECT nextid INTO id FROM maxid WHERE name = table_name;
		-- 返回函数处理结果
		RETURN id;
	END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
