package com.silverhu.authority.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.utils.page.Pageable;
import com.silverhu.authority.entity.SysPermission;
import com.silverhu.authority.entity.SysRole;

public interface SysRoleDao {

    int add(SysRole entity);

    /**
     * 批量添加角色权限关系
     * 
     * @param id
     * @param list
     * @return
     */
    int addPermissionRelation(@Param("id") Long id, @Param("list") List<SysPermission> list);

    int update(SysRole entity);

    int deleteById(Long id);

    /**
     * 根据角色id，删除该用户的所有权限
     * 
     * @param id
     * @return
     */
    int deletePermissionRelation(Long id);

    SysRole findById(Long id);

    SysRole findOne(@Param("condition") SysRole condition);

    List<SysRole> findByCondition(@Param("condition") SysRole condition, @Param("pageable") Pageable pageable);

    int findCount(@Param("condition") SysRole condition);

}
