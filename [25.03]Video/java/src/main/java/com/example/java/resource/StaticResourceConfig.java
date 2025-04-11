package com.example.java.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 비디오 파일 경로 (Windows 경로)
        registry.addResourceHandler("/media/**")
            .addResourceLocations("file:///C:/videos/") // 윈도우: 반드시 세 개의 슬래시!
            .setCachePeriod(3600); // 캐시 (초 단위)

        // 정적 자원 (내부 classpath)
        registry.addResourceHandler("/assets/**")
            .addResourceLocations("classpath:/static/assets/")
            .setCachePeriod(3600);

        // 하나의 URL 여러 경로
        registry.addResourceHandler("/static/**")
            .addResourceLocations(
                "classpath:/static/common/",     // 1순위
                "file:///C:/custom-resources/",  // 2순위
                "classpath:/static/default/"     // 3순위
            );
    }
}
