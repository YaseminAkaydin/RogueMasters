package de.roguemaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class EnemyServiceApplication{
    public static void main(String[] args) {
        SpringApplication.run(EnemyServiceApplication.class, args);
    }
}