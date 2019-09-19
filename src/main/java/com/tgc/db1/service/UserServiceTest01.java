
package com.tgc.db1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgc.db1.dao.UserMapperTest01;
import com.tgc.db2.dao.UserMapperTest02;
import com.tgc.db2.service.UserServiceTest02;

@Service
public class UserServiceTest01 {
	@Autowired
	private UserMapperTest01 userMapperTest01;
	@Autowired
	private UserMapperTest02 userMapperTest02;

	// private UserServiceTest02 userServiceTest02;
	@Transactional
	public String insertTest001(String name, Integer age) {
		userMapperTest01.insert(name, age);
		// userServiceTest02.insertTest002(name, age);	//使用这个service可以回滚(insertTest002使用事务注解)。 事务传播行为
		userMapperTest02.insert(name, age);	//如果没有使用atomikos进行分布式事物管理，  这一条会插入到数据库 而不回滚。
		int i = 1 / 0;	//异常
		return "success";
	}

	@Async	//异步注解
	public String sendSms() {
		// new UserThread().start();
		System.out.println("***sendSms***1");
		System.out.println("***sendSms***");
		System.out.println("***sendSms***2");
		return "success";
	}

	// class UserThread extends Thread {
	// @Override
	// public void run() {
	// System.out.println("###sendSms###3");
	// for (int i = 1; i <= 3; i++) {
	// System.out.println("i:" + i);
	// }
	// System.out.println("###sendSms###4");
	// return "success";
	// }
	// }

}
