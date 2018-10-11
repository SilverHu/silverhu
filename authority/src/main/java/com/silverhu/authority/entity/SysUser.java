package com.silverhu.authority.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 系统用户
 *
 * @author huchuanyin
 */
@Data
public class SysUser implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 用户id */
	private Long id;

	/** 用户名 */
	private String username;

	/** 用户姓名 */
	private String name;

	/** 密码 */
	private String password;

	/** 用户邮箱 */
	private String email;

	/** 保留字符 0-禁用 1-启用 默认0 */
	private Byte status;

	/** 角色列表 */
	private List<SysRole> roleList;

	/** 权限列表 */
	private List<SysPermission> permissionList;

}
