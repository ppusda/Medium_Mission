package com.ll.medium_mission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MediumMissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediumMissionApplication.class, args);
    }

}
