package com.hyunbenny.security.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MustacheViewResolver viewResolver = new MustacheViewResolver();
        viewResolver.setCharset("UTF-8");
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");

        registry.viewResolver(viewResolver);
    }
}
