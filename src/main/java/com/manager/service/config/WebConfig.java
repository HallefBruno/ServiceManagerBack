package com.manager.service.config;

import java.util.Arrays;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {
    
    @Bean
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean() {
        var allCors = Arrays.asList("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(allCors);
        corsConfiguration.setAllowedHeaders(allCors);
        corsConfiguration.setAllowedMethods(allCors);
        
        source.registerCorsConfiguration("/**", corsConfiguration);
        CorsFilter corsFilter = new CorsFilter(source);
        
        FilterRegistrationBean<CorsFilter> frb = new FilterRegistrationBean<>(corsFilter);
        frb.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return frb;
    }
    
}
