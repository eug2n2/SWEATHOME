package com.ssafy.ssafit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import com.ssafy.ssafit.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
//	
//	//주입하는 방법 3가지 중 아주 심플하게 주입해보자
//	@Autowired
//	private JwtInterceptor jwtInterceptor;
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/api-user/**","/swagger-ui/**","/v3/api-docs/**");
//	}
//	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");;
		
	}
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	
}
