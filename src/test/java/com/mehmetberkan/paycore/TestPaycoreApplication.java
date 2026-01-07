package com.mehmetberkan.paycore;

import org.springframework.boot.SpringApplication;

public class TestPaycoreApplication {

    public static void main(String[] args) {
        SpringApplication.from(PaycoreApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
