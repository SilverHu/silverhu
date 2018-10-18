package com.silverhu.authority.service;

import java.util.List;
import java.util.Optional;

import com.common.utils.page.Page;
import com.common.utils.page.Pageable;
import com.silverhu.authority.entity.SysRole;

public interface SysRoleService {

    int save(SysRole entity);

    int deleteById(Long id);

    Optional<SysRole> findById(Long id);

    List<SysRole> findByCondition(SysRole condition);

    Page<SysRole> findByPage(Pageable pageable, SysRole condition);

    /**
     * 判断角色名是否唯一
     * 
     * @param id
     * @param name
     * @return
     */
    boolean isUnique(Long id, String name);
}
