package com.facade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if (profiles != null) {
            for (String profile : profiles) {
                System.out.println("------------start with profile : " + profile);
            }
        }
    }
}
