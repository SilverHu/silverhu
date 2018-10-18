package com.silverhu.authority.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.silverhu.authority.entity.SysPermission;
import com.silverhu.authority.repository.SysPermissionDao;
import com.silverhu.authority.service.SysPermissionService;

public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Transactional
    @Override
    public int save(SysPermission entity) {
        if (entity == null) {
            return 0;
        }
        if (entity.getId() != null) {
            return sysPermissionDao.update(entity);
        } else {
            return sysPermissionDao.add(entity);
        }
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return sysPermissionDao.deleteById(id);
    }

    @Override
    public Optional<SysPermission> findById(Long id) {
        return Optional.ofNullable(sysPermissionDao.findById(id));
    }

    @Override
    public List<SysPermission> findByCondition(SysPermission condition) {
        return sysPermissionDao.findByCondition(condition, null);
    }

    @Override
    public boolean isUnique(Long id, String permission) {
        if (StringUtils.isBlank(permission)) {
            return true;
        }
        SysPermission condition = new SysPermission();
        condition.setPermission(permission);
        SysPermission entity = sysPermissionDao.findOne(condition);
        if (entity.getId() == null) {
            return true;
        } else if (entity.getId().equals(id)) {
            return true;
        } else {
            return false;
        }
    }

}
