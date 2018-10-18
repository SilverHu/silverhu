package com.silverhu.authority.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.utils.page.Pageable;
import com.silverhu.authority.entity.SysPermission;
import com.silverhu.authority.entity.SysRole;
import com.silverhu.authority.entity.SysUser;

public interface SysUserDao {

    int add(SysUser entity);

    int update(SysUser entity);

    int deleteById(Long id);

    SysUser findById(Long id);

    SysUser findOne(@Param("condition") SysUser condition);

    List<SysUser> findByCondition(@Param("condition") SysUser condition, @Param("pageable") Pageable pageable);

    int findCount(@Param("condition") SysUser condition);

    /**
     * 批量添加用户角色关系
     * 
     * @param id
     * @param list
     * @return
     */
    int addPermissionRelation(@Param("id") Long id, @Param("list") List<SysPermission> list);

    /**
     * 根据用户id，删除该用户的所有权限
     * 
     * @param id
     * @return
     */
    int deletePermissionRelation(Long id);

    /**
     * 批量添加用户角色关系
     * 
     * @param id
     * @param list
     * @return
     */
    int addRoleRelation(@Param("id") Long id, @Param("list") List<SysRole> list);

    /**
     * 根据用户id，删除该用户的所有角色
     * 
     * @param id
     * @return
     */
    int deleteRoleRelation(Long id);

}
