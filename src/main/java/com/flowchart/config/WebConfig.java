package com.flowchart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @className: webConfig
 * @Description: 静态资源映射
 * @author: ct
 * @date: 2022/3/30 15:33
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Value("${upload.uploadAddr}")
    private String uploadAddr;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        //前缀+真实地址
        registry.addResourceHandler("/flowchart/**")
        .addResourceLocations("file:" + uploadAddr);
    }
}
