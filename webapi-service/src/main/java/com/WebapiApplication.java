package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description
 * @author: yangyingyang
 * @date: 2020/4/30.
 */
@ComponentScan("com")
@EntityScan("com")
@SpringBootApplication
@EnableSwagger2
@ServletComponentScan
public class WebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebapiApplication.class);
    }
}
