package com.douzone.jblog.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


public class MvcConfig extends WebMvcConfigurerAdapter {

	// ViewResolver
	//	   <!-- ViewResolver 설정 --> 
	//	   <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	//	      <property name="viewClass" value="org.springframework.web.servlet.view.JstlView" />
	//	      <property name="prefix" value="/WEB-INF/views/" />
	//	      <property name="suffix" value=".jsp" />
	//	   </bean>
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setExposeContextBeansAsAttributes(true);

		return viewResolver;
	}

	// Default Servlet Handler
	// <!-- 서블릿 컨테이너의 디폴트 서블릿 위임 핸들러 -->
	// <mvc:default-servlet-handler />
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	//	   <!-- mvc resources -->
	//	   <mvc:resources location="file:/jblog-upload/" mapping="/assets/image/**" />
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/image/**").addResourceLocations("file:/jblog-upload/");
	}
}
