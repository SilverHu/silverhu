package com.silverhu.authority.service;

import java.util.Optional;

import com.common.utils.page.Page;
import com.common.utils.page.Pageable;
import com.silverhu.authority.entity.SysUser;

public interface SysUserService {

    /**
     * 添加/全量编辑
     * 
     * @param entity
     */
    int save(SysUser entity);

    int deleteById(Long id);

    /**
     * 全量查询
     * 
     * @param id
     * @return
     */
    Optional<SysUser> findById(Long id);

    Optional<SysUser> findByUsername(String username);

    /**
     * 分页查询，查询条件：username模糊，status状态
     * 
     * @param pageable
     * @param sysUser
     * @return
     */
    Page<SysUser> findByPage(Pageable pageable, SysUser condition);

    /**
     * 判断用户名是否唯一
     * 
     * @param id
     * @param username
     * @return
     */
    boolean isUnique(Long id, String username);
}
