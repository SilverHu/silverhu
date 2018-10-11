package com.silverhu.authority.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SysPermission implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 资源id */
    private Long id;

    /** 资源名称 */
    private String name;

    /** 父资源id */
    private Long parentid;

    /** 权限标识 */
    private String permission;

    /** 资源类型 1-菜单 2-按钮 */
    private Byte type;

    /** 子菜单 */
    private List<SysPermission> subPermissionList;

}
