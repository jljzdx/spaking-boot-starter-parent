package com.spaking.boot.starter.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 获取配置文件中的属性
 * @author XIEZHONG
 *
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class AppConfig {
    
    public static String APPLICATION_NAME;
    
    public AppConfig(ApplicationProperties properties) {
        APPLICATION_NAME = properties.getName();
    }
    
//    @Value("${spring.application.name:app-default-name}")
//    private String applicationName;
//
//    @Autowired(required = true)
//    void setAppConfig() {
//        AppConfig.APPLICATION_NAME = getApplicationName();
//    }
//    
//    private String getApplicationName() {
//        return applicationName;
//    }
}
