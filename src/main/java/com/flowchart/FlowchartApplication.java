package com.flowchart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 在线协同
 * @author chentuo
 */
@SpringBootApplication
@MapperScan("com.flowchart.mapper")
public class FlowchartApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowchartApplication.class, args);
    }

}
