package com.wyj.blog.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "blog")
@ToString
@Data
public class BlogUserConfig {
    private String username;
    private String password;
    private Long ttlMinutes;
}
