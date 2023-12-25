package com.ll.medium_mission;

import com.ll.medium_mission.global.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@EnableConfigurationProperties({AppConfig.class})
class MediumMissionApplicationTests {

    @Test
    void contextLoads() {
    }

}
