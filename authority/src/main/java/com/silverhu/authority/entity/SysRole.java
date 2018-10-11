package com.silverhu.authority.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SysRole implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 角色id */
    private Long id;// 

    /** 角色名称 */
    private String name;

    /** 权限集合 */
    private List<SysPermission> permissionList;

}
