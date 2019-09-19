
package com.tgc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//标识该接口全部返回json格式。
@RestController
public class HelloWorldController {
	
	
	@RequestMapping("/indexHelloworld")
	public String index() {
		return "success";
	}

	
	@RequestMapping("/getMap")
	public Map<String, Object> getMap() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("errorCode", "200");
		result.put("errorMsg", "成功..");
		return result;
	}


}
