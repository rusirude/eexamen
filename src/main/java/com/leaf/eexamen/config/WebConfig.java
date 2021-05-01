package com.leaf.eexamen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       
    	registry.addResourceHandler(                
                "/images/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(            
                		"classpath:/static/images/",
                        "classpath:/static/css",
                        "classpath:/static/css/config",
                        "classpath:/static/css/webfonts",
                        "classpath:/static/css/config/font",
                        "classpath:/static/js",
                        "classpath:/static/js/config");
    }
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
}
}
