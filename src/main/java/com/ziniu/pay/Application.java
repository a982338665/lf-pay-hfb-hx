package com.ziniu.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan({"com.ziniu.pay.dao"})
@ComponentScan(basePackages={"com.ziniu.pay.*"})
@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
