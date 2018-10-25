
package com.tgc.yml.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexYmController {

	@RequestMapping("/getTest")
	public String getTest() {
		return "success";
	}

}
