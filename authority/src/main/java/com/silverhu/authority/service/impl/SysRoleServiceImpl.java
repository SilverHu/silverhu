package com.silverhu.authority.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.page.Page;
import com.common.utils.page.Pageable;
import com.silverhu.authority.entity.SysRole;
import com.silverhu.authority.repository.SysRoleDao;
import com.silverhu.authority.service.SysRoleService;

public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Transactional
    @Override
    public int save(SysRole entity) {
        if (entity == null) {
            return 0;
        }
        int num = 0;
        if (entity.getId() != null) {
            num = sysRoleDao.update(entity);
        } else {
            num = sysRoleDao.add(entity);
        }
        if (num > 0) {
            sysRoleDao.deletePermissionRelation(entity.getId());
            sysRoleDao.addPermissionRelation(entity.getId(), entity.getPermissionList());
        }
        return num;
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        int num = sysRoleDao.deleteById(id);
        if (num > 0) {
            sysRoleDao.deletePermissionRelation(id);
        }
        return num;
    }

    @Override
    public Optional<SysRole> findById(Long id) {
        return Optional.ofNullable(sysRoleDao.findById(id));
    }

    @Override
    public List<SysRole> findByCondition(SysRole condition) {
        return sysRoleDao.findByCondition(condition, null);
    }

    @Override
    public Page<SysRole> findByPage(Pageable pageable, SysRole condition) {
        return new Page<>(pageable, sysRoleDao.findByCondition(condition, pageable), sysRoleDao.findCount(condition));
    }

    @Override
    public boolean isUnique(Long id, String name) {
        if (StringUtils.isBlank(name)) {
            return true;
        }
        SysRole condition = new SysRole();
        condition.setName(name);
        SysRole entity = sysRoleDao.findOne(condition);
        if (entity.getId() == null) {
            return true;
        } else if (entity.getId().equals(id)) {
            return true;
        } else {
            return false;
        }
    }

}
