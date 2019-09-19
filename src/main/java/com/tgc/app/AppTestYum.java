
package com.tgc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试使用ym配置文件。   （同时也会读到application.properties文件的配置）
 * ym可以树形（不会重复spring.datasource.username、spring.datasource.password这样的重复spring.datasource）
 * @author Administrator
 *
 */
@ComponentScan(basePackages = { "com.tgc.yml.controller" })
@EnableAutoConfiguration
public class AppTestYum {

	/*public static void main(String[] args) {
		// 主函数运行springboot项目
		SpringApplication.run(AppTestYm.class, args);
		//ym配置了访问端口8888   http://localhost:8888/tgc/getTest
		//
	}*/

}
