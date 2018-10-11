package com.test.facade;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional // 在单元测试执行完成后会自动回滚至初始状态
public class MyJdbcTempleteTest extends BaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void query() {
        jdbcTemplate.execute("insert into test(b) values(1)");
        Integer i = jdbcTemplate.queryForObject("select count(id) from test", Integer.class);
        System.out.println(i);
    }
}
