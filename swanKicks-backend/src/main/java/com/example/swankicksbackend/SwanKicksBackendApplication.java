package com.example.swankicksbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SwanKicksBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwanKicksBackendApplication.class, args);
    }

}
