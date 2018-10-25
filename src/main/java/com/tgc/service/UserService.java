
package com.tgc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgc.db1.dao.UserMapperTest01;

@Service
public class UserService {
	@Autowired
	private UserMapperTest01 userMapperTest01;
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;	//此jdbcTemplate使用的数据库为test
	
	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate2;	//此jdbcTemplate使用的数据库为test2
	
	/**
	 * 测试事务是否回滚
	 * @param name
	 * @param age
	 */
	@Transactional
	public void testTransaction(String name, Integer age) {
		System.out.println("testTransaction");
		jdbcTemplate.update("insert into users values(null,?,?);", name, age);
		int a = 1/0;
		System.out.println("创建用户成功...");
	}
	
	public void createUser(String name, Integer age) {
		System.out.println("createUser");
		jdbcTemplate.update("insert into users values(null,?,?);", name, age);
		System.out.println("创建用户成功...");
	}
	
	public void createUser2(String name, Integer age) {
		System.out.println("createUser2");
		jdbcTemplate2.update("insert into users values(null,?,?);", name, age);
		System.out.println("创建用户成功...");
	}
	
	/**
	 * 因userMapperTest01的数据源使用Atomikos创建，
	 * 而jdbcTemplate2的数据源也是由Atomikos创建，
	 * 此处测试是否能够事务回滚 —— 结果：也能！！
	 * @param name
	 * @param age
	 * @return
	 */
	@Transactional
	public String insertTest003(String name, Integer age) {
		userMapperTest01.insert(name, age);	//test1数据库
		// userServiceTest02.insertTest002(name, age);	//使用这个service可以回滚。 事务传播行为
		jdbcTemplate2.update("insert into users values(null,?,?);", name, age);	//test2数据库
		//如果没有使用atomikos进行分布式事物管理，  这一条会插入到数据库 而不回滚。
		
		int i = 1 / 0;	//异常
		return "success";
	}

}
