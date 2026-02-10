package com.mehmetberkan.paycore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class PaycoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaycoreApplication.class, args);
    }

}
