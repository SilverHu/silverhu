package com.silverhu.authority.service;

import java.util.List;
import java.util.Optional;

import com.silverhu.authority.entity.SysPermission;

public interface SysPermissionService {

    int save(SysPermission entity);

    int deleteById(Long id);

    Optional<SysPermission> findById(Long id);

    List<SysPermission> findByCondition(SysPermission condition);

    /**
     * 判断权限标识是否唯一
     * 
     * @param id
     * @param permission
     * @return
     */
    boolean isUnique(Long id, String permission);
}
