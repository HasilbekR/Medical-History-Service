package com.example.medicalhistoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MedicalHistoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalHistoryServiceApplication.class, args);
    }

}
