package com.silverhu.authority.test.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.silverhu.authority.repository.SysPermissionDao;
import com.silverhu.authority.test.BaseTest;

public class SysPermissionDaoTest extends BaseTest{
    
    @Autowired
    private SysPermissionDao sysPermissionDao;

    public void add() {
        
    }

    public void update() {
        
    }

    public void deleteById(Long id) {
        
    }

    public void findById(Long id) {
        
    }

    public void findOne() {
        
    }

    @Test
    public void findByCondition(){
        System.out.println(sysPermissionDao.findByCondition(null, null));
    }

}
