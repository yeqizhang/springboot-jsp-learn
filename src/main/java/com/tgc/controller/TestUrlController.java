
package com.tgc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试设置类访问路径  （测试@RequestMapping写在类名上）
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/test/*")
public class TestUrlController {

	/**
	 * 测试跳转到jsp页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
		//http://localhost:8888/tgc/test/index
	}
	
}
