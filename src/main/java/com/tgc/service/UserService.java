
package com.tgc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgc.dao.UserDao;
import com.tgc.db1.dao.UserMapperTest01;
import com.tgc.entity.User;

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
	
	@Autowired
	private UserDao userDao;
	
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
	
	/**
	 * 因userDao的数据源使用Atomikos创建，
	 * 而jdbcTemplate2的数据源也是由Atomikos创建，
	 * 此处测试是否能够事务回滚 —— 结果：也能！！
	 * @param name
	 * @param age
	 * @return
	 */
	@Transactional
	public String insertTest004(String name, Integer age) {
		User newUser = new User();
        newUser.setName(name);
        newUser.setAge(age);
        userDao.save(newUser);	//test1数据库
		jdbcTemplate2.update("insert into users values(null,?,?);", name, age);	//test2数据库
		//如果没有使用atomikos进行分布式事物管理，  这一条会插入到数据库 而不回滚。
		
		int i = 1 / 0;	//异常
		return "success";
	}
	
	//value 属性是必需要有的，它表示当前方法的返回值会被存在哪个Cache 上，对应的是Cache 的名称。当然前面说过可以同时缓存在多个缓存上。
	//value并不是说指定的是redis容器还是ehcache
	@Cacheable(value = "cache2", key = "#id")	
	//如果使用echache，这里的value必须是ehcache中配置的name。 spring也自带缓存组件，无需提前配置value。 redis也无需提前配置此value。
	public User findById(int id){
		User user = userDao.findOne(id);	//最好实现序列化 Serializable 接口，这样方便切换到分布式的各个缓存系统。
		System.err.println("第一次查询走数据库。。。");	  // 使用redis数据库删除后，不会再到此方法里。
		return user;
	}

	//下列方法无效原因：使用@CacheEvict注解的方法必须是controller层直接调用，service里间接调用不生效
	/**
     * allEntries = true: 清空cache2里的所有缓存
     * 
     *  allEntries = true: 清空缓存book1里的所有值
    	allEntries = false: 默认值，此时只删除key对应的值
     */
    /*@CacheEvict(cacheNames="cache2", allEntries=true)
    public void clearCache2All(){	//此注解暂时没用。 使用redis数据库删除有效果。
        System.out.println("cache2 clear");
    }*/
}
