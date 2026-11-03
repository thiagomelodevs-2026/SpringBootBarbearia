package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.demo", "br.edu.faculdade.vagas"})
@EntityScan(basePackages = {"com.example.demo.entity", "br.edu.faculdade.vagas.entity"})
@EnableJpaRepositories(basePackages = {"com.example.demo.repository", "br.edu.faculdade.vagas.repository"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
