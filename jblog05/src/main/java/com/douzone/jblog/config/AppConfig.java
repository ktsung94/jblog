package com.douzone.jblog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.jblog.config.app.DBConfig;
import com.douzone.jblog.config.app.MyBatisConfig;


@Configuration
//<!-- auto proxy -->
//<aop:aspectj-autoproxy />
@EnableAspectJAutoProxy
//<context:component-scan
//base-package="com.douzone.jblog.service, com.douzone.jblog.repository, com.douzone.jblog.aspect">
//<context:include-filter type="annotation"
//	expression="org.springframework.stereotype.Repository" />
//<context:include-filter type="annotation"
//	expression="org.springframework.stereotype.Service" />
//<context:include-filter type="annotation"
//	expression="org.springframework.stereotype.Component" />
//</context:component-scan>
@ComponentScan({"com.douzone.jblog.service", "com.douzone.jblog.repository", "com.douzone.jblog.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {

}
