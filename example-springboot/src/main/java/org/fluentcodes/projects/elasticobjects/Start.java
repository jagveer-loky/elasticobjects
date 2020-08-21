package org.fluentcodes.projects.elasticobjects;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by werner.diwischek on 11.12.17.
 */

//https://stackoverflow.com/questions/24661289/spring-boot-not-serving-static-content
@SpringBootApplication
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}

