package com.tgc.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource("classpath:db.properties")
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseZeroProperties extends DatabaseProperties{
	
    private String driverClassName;	//使用MysqlXADataSource可以不用此属性
    
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
    
    
}
