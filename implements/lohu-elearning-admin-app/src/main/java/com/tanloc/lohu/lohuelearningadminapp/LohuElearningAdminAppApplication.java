package com.tanloc.lohu.lohuelearningadminapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LohuElearningAdminAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LohuElearningAdminAppApplication.class, args);
    }

}
