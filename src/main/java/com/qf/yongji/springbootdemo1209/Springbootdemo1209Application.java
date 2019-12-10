package com.qf.yongji.springbootdemo1209;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qf.yongji.springbootdemo1209.dao")//开启mybatis的dao层接口扫描
public class Springbootdemo1209Application {
    public static void main(String[] args) {
        SpringApplication.run(Springbootdemo1209Application.class, args);
    }

}
