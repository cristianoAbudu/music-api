package com.cristianoalmeida.musicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MusicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApiApplication.class, args);
    }

}
