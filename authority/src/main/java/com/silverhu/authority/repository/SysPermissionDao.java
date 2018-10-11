package com.silverhu.authority.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.utils.page.Pageable;
import com.silverhu.authority.entity.SysPermission;

public interface SysPermissionDao {

    int add(SysPermission entity);

    int update(SysPermission entity);

    int deleteById(Long id);

    SysPermission findById(Long id);

    SysPermission findOne(@Param("condition") SysPermission condition);

    List<SysPermission> findByCondition(@Param("condition") SysPermission condition,
            @Param("pageable") Pageable pageable);

}
