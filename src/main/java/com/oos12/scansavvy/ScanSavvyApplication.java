package com.oos12.scansavvy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.oos12.scansavvy.health.repository", "com.oos12.scansavvy.member.repository"})
@Import({com.oos12.scansavvy.config.MongoConfig.class, com.oos12.scansavvy.config.RestClientConfig.class})
public class ScanSavvyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScanSavvyApplication.class, args);
    }

}
