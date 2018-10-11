package com.silverhu.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动入口类
 * 
 * @author sliverhu
 *
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@MapperScan("com.silverhu.authority.repository")
public class Application extends SpringBootServletInitializer {

    /**
     * war包启动
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    /**
     * jar包启动
     * 
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if (profiles != null) {
            for (String profile : profiles) {
                log.debug("------------start with profile : " + profile);
            }
        }
    }
}
