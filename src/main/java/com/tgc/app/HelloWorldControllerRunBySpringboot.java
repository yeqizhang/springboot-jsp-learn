
package com.tgc.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单个Controller直接用springboot运行   （可直接运行此类的main方法）
 * @author Administrator
 *
 */
@EnableAutoConfiguration	//此注解可以让springboot用tomcat运行
@RestController		//标识该接口全部返回json格式。
public class HelloWorldControllerRunBySpringboot {
	
	/*
	//去掉此注释
	public static void main(String[] args) {
		//主函数运行springboot项目
		SpringApplication.run(HelloWorldControllerRunBySpringboot.class, args);
		//运行后访问http://localhost:8888/tgc/index
	}
	*/
	
	@RequestMapping("/index1")
	public String index1() {
		return "success";
	}

	@RequestMapping("/getMap1")
	public Map<String, Object> getMap() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errorCode", "200");
		result.put("errorMsg", "成功..");
		return result;
	}

}
