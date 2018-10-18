package com.silverhu.authority.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.page.Page;
import com.common.utils.page.Pageable;
import com.silverhu.authority.entity.SysUser;
import com.silverhu.authority.repository.SysUserDao;
import com.silverhu.authority.service.SysUserService;

public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Transactional
    @Override
    public int save(SysUser entity) {
        if (entity == null) {
            return 0;
        }
        if (entity.getId() != null) {
            return sysUserDao.update(entity);
        } else {
            return sysUserDao.add(entity);
        }
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return sysUserDao.deleteById(id);
    }

    @Override
    public Optional<SysUser> findById(Long id) {
        return Optional.ofNullable(sysUserDao.findById(id));
    }

    @Override
    public Optional<SysUser> findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return Optional.ofNullable(null);
        }
        SysUser condition = new SysUser();
        condition.setUsername(username);
        return Optional.ofNullable(sysUserDao.findOne(condition));
    }

    @Override
    public Page<SysUser> findByPage(Pageable pageable, SysUser condition) {
        return new Page<>(pageable, sysUserDao.findByCondition(condition, pageable), sysUserDao.findCount(condition));
    }

    @Override
    public boolean isUnique(Long id, String username) {
        if (StringUtils.isBlank(username)) {
            return true;
        }
        SysUser condition = new SysUser();
        condition.setUsername(username);
        SysUser entity = sysUserDao.findOne(condition);
        if (entity.getId() == null) {
            return true;
        } else if (entity.getId().equals(id)) {
            return true;
        } else {
            return false;
        }
    }

}
