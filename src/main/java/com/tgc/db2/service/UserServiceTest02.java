
package com.tgc.db2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgc.db1.dao.UserMapperTest01;
import com.tgc.db2.dao.UserMapperTest02;

@Service
public class UserServiceTest02 {
	@Autowired
	private UserMapperTest02 userMapperTest02;

	@Transactional
	public String insertTest002(String name, Integer age) {
		userMapperTest02.insert(name, age);
		int i =1/0;
		return "success";
	}

}
