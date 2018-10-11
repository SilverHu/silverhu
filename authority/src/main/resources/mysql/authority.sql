CREATE DATABASE IF NOT EXISTS `authority` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
USE `authority`;

/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : authority

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 11/10/2018 17:21:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '资源名称',
  `parentid` bigint(20) NOT NULL DEFAULT 0 COMMENT '父资源id',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '资源类型 1-菜单 2-按钮',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_permission`(`permission`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统资源' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '系统设置', 0, 'sys:read', 1);
INSERT INTO `sys_permission` VALUES (2, '用户管理', 1, 'user:get', 1);
INSERT INTO `sys_permission` VALUES (3, '角色管理', 1, 'role:get', 1);
INSERT INTO `sys_permission` VALUES (4, '资源管理', 1, 'permission:get', 1);
INSERT INTO `sys_permission` VALUES (5, '编辑资源', 4, 'permission:save', 2);
INSERT INTO `sys_permission` VALUES (6, '编辑用户', 2, 'user:save', 2);
INSERT INTO `sys_permission` VALUES (7, '删除用户', 2, 'user:delete', 2);
INSERT INTO `sys_permission` VALUES (8, '编辑角色', 3, 'role:save', 2);
INSERT INTO `sys_permission` VALUES (9, '删除角色', 3, 'role:delete', 2);
INSERT INTO `sys_permission` VALUES (10, '删除资源', 4, 'permission:delete', 2);

-- ----------------------------
-- Table structure for sys_relation_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation_role_permission`;
CREATE TABLE `sys_relation_role_permission`  (
  `roleid` int(20) NOT NULL COMMENT '角色id',
  `permissionid` int(20) NOT NULL COMMENT '资源id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色权限' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_relation_role_permission
-- ----------------------------
INSERT INTO `sys_relation_role_permission` VALUES (1, 1);
INSERT INTO `sys_relation_role_permission` VALUES (1, 2);
INSERT INTO `sys_relation_role_permission` VALUES (1, 6);
INSERT INTO `sys_relation_role_permission` VALUES (1, 7);
INSERT INTO `sys_relation_role_permission` VALUES (1, 3);
INSERT INTO `sys_relation_role_permission` VALUES (1, 8);
INSERT INTO `sys_relation_role_permission` VALUES (1, 9);
INSERT INTO `sys_relation_role_permission` VALUES (1, 4);
INSERT INTO `sys_relation_role_permission` VALUES (1, 5);
INSERT INTO `sys_relation_role_permission` VALUES (1, 10);

-- ----------------------------
-- Table structure for sys_relation_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation_user_permission`;
CREATE TABLE `sys_relation_user_permission`  (
  `userid` int(20) NOT NULL COMMENT '用户id',
  `permissionid` int(20) NOT NULL COMMENT '资源id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_relation_user_permission
-- ----------------------------
INSERT INTO `sys_relation_user_permission` VALUES (1, 1);
INSERT INTO `sys_relation_user_permission` VALUES (1, 2);
INSERT INTO `sys_relation_user_permission` VALUES (1, 3);
INSERT INTO `sys_relation_user_permission` VALUES (1, 4);
INSERT INTO `sys_relation_user_permission` VALUES (1, 5);
INSERT INTO `sys_relation_user_permission` VALUES (1, 6);
INSERT INTO `sys_relation_user_permission` VALUES (1, 7);
INSERT INTO `sys_relation_user_permission` VALUES (1, 8);
INSERT INTO `sys_relation_user_permission` VALUES (1, 9);
INSERT INTO `sys_relation_user_permission` VALUES (1, 10);

-- ----------------------------
-- Table structure for sys_relation_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation_user_role`;
CREATE TABLE `sys_relation_user_role`  (
  `userid` int(20) NOT NULL COMMENT '用户id',
  `roleid` int(20) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_relation_user_role
-- ----------------------------
INSERT INTO `sys_relation_user_role` VALUES (1, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '保留字符 0-禁用 1-启用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '2984d81aa698559ffe298c676fb6b3f4', '超级管理员', '', 1);

SET FOREIGN_KEY_CHECKS = 1;
