
package com.tgc.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tgc.dao.UserDao;
import com.tgc.db1.dao.UserMapperTest01;
import com.tgc.db1.service.UserServiceTest01;
import com.tgc.db2.dao.UserMapperTest02;
import com.tgc.entity.User;
import com.tgc.mapper.UserMapper;
import com.tgc.service.UserService;

/**
 * 此类的分布式事务，适用于单机多数据源的情况。
 * @author Administrator
 *
 */
@Controller
public class IndexController {
	private static Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserMapperTest01 userMapperTest01;
	@Autowired
	private UserMapperTest02 userMapperTest02;
	@Autowired
	private UserServiceTest01 userServiceTest01;
	@Autowired
	private CacheManager cacheManager;
	
	@Value("${my.name}")	//取配置文件中的配置
	private String myname;

	/**
	 * 测试跳转到jsp页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
		//http://localhost:8888/tgc/index
	}
	/**
	 * 测试跳转到jsp页面
	 * @return
	 */
	@RequestMapping("/loveyou")
	public String loveyou() {	// http://localhost:8888/tgc/loveyou
		return "loveyou";
	}
	
	/**
	 * 使用el表达式 以及jstl
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView  list() {
		List<User> list = userDao.findAll();
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		return mav;
		//http://localhost:8888/tgc/list
	}
	
	//==========使用jdbctemplate===============
	/**
	 * 使用jdbcTemplate插入数据库  test
	 * @param name
	 * @param age
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createUser")
	public String createUser(String name, Integer age) {
		userService.createUser(name, age);
		//http://localhost:8888/tgc/createUser?name=tgc33&age=26
		return "success";
	}
	
	/**
	 * 使用jdbcTemplate2插入数据库  test2
	 * @param name
	 * @param age
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createUser2")
	public String createUser2(String name, Integer age) {
		userService.createUser2(name, age);
		//http://localhost:8888/tgc/createUser2?name=tgc33&age=26
		return "success";
	}
	
	/**
	 * 使用Atomikos数据源的mybatis 插入数据库 test1,使用jdbcTemplate2插入数据库  test2
	 * 测试是否事务回滚。   可以回滚！！！！！！  
	 * @param name
	 * @param age
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createUser3")
	public String createUser3(String name, Integer age) {
		userService.insertTest003(name, age);
		//http://localhost:8888/tgc/createUser3?name=tgc44&age=26
		return "success";
	}
	//==============================================
	
	//==================SpringDataJpa==========================
	/**
	 * 测试SpringDataJpa配置数据源
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testSpringDataJpa")
	public String testSpringDataJpa() {
		User user =userDao.findOne(1);
		//http://localhost:8888/tgc/testSpringDataJpa
		return user.getName();
	}
	/**
	 * 测试SpringDataJpa插入数据到数据源中
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createUserBySpringDataJpa")
	public String createUserBySpringDataJpa()  {
        User newUser = new User();
        newUser.setName("test1");
        userDao.save(newUser);
        return newUser.getId().toString();
      //http://localhost:8888/tgc/createUserBySpringDataJpa
    }
	
	/**
	 * 使用Atomikos的userDao 插入数据库 test1,使用jdbcTemplate2插入数据库  test2
	 * 测试是否分布式事务回滚。    可以回滚！！！！！！  
	 * @param name
	 * @param age
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createUser4")
	public String createUser4(String name, Integer age) {
		userService.insertTest004(name, age);
		//http://localhost:8888/tgc/createUser4?name=tgc44&age=26
		return "success";
	}
	//============================================
	
	
	//============使用mybatis=========================
	@ResponseBody
	@RequestMapping("/findByName")
	public User findByName(String name) {
		log.info("####findByName()####name:" + name);
		return userMapper.findByName(name);
		//http://localhost:8888/tgc/findByName?name=tgc
		//此处有误。  （已解决，把 MybatisDbMasterConfig给userMapper配置了数据源,但会影响Atomikos的事务管理，无法回滚。）
	}
	
	/**
	 * 测试mybatis配置的数据源是否能够回滚事务
	 * @param name
	 * @param age
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testTransaction")
	public String testTransaction(String name, Integer age) {
		userService.testTransaction(name, age);
		return "success";
		//http://localhost:8888/tgc/testTransaction?name=testTransaction&age=18
	}

	/*@ResponseBody
	@RequestMapping("/insert")
	public String insert(String name, Integer age) {
		userMapper.insert(name, age);
		return "success";
	}*/

	/**
	 * 测试分布式事务处理
	 * @param name
	 * @param age
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insertTest001")
	public String insertTest001(String name, Integer age) {
		userServiceTest01.insertTest001(name, age);
		//http://localhost:8888/tgc/insertTest001?name=tgc66&age=18
		return "success";
	}
	
	/**
	 * ehcache缓存测试
	 * @测试方法：
	 * 1、首先浏览器调用此方法缓存name=tgc1的数据；
	 * 2、数据库删除name=tgc1的数据；
	 * 3、浏览器调用http://localhost:8888/tgc/findByNameTest01?name=tgc1 会发现还能查到数据。
	 * 清除再测试
	 * 4、浏览器调用http://localhost:8888/tgc/removeKey 清除缓存；
	 * 5、再次调用此方法http://localhost:8888/tgc/findByNameTest01?name=tgc1
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findByNameTest01")
	public User findByNameTest01(String name) {
		log.info("####findByName()####name:" + name);
		return userMapperTest01.findByName(name);
		//http://localhost:8888/tgc/findByNameTest01?name=tgc1
		/*
		恢复数据：
		use test1;
		INSERT INTO `users` VALUES ('1', 'tgc1', '26');
		*/
	}
	
	/**
	 * 测试redis缓存
	 * 
	 * 调用后第一次会进入userService.findById打印里面的语句
	 * 第二次直接查缓存，不进入userService.findById方法体类。
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/testRedisCache")
	public User testRedisCache(Integer id) {
		log.info("####findById()####id:" + id);
		return userService.findById(id);
		//http://localhost:8888/tgc/testRedisCache?id=1
		/*
		恢复数据：
		use test1;
		INSERT INTO `users` VALUES ('1', 'tgc1', '26');
		*/
	}

	@ResponseBody
	@RequestMapping("/insertTest002")
	public String insertTest002(String name, Integer age) {
		userMapperTest02.insert(name, age);
		return "success";
	}

	/**
	 * 测试异步注解的方法效果
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendMsg")
	public String sendMsg() {
		System.out.println("###sendMsg###1");
		userServiceTest01.sendSms();	//此方法不阻塞  此方法不阻塞  此方法不阻塞
		System.out.println("###sendMsg###2");
		return "success";
		//http://localhost:8888/tgc/sendMsg
		
		//控制台打印结果：
		//###sendMsg###1
		//###sendMsg###2
		//***sendSms***1
		//***sendSms***
		//***sendSms***2
	}
	
	/**
	 * 测试采用@Value注解获取配置文件的配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getValue")
	public String getValue() {
		return myname;
	}

	/**
	 * 给前台调用清除缓存的方法1 （对redis不管用。。）
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/removeKey")
	public String removeKey(String key) {
		System.out.println(cacheManager.getCacheNames());;
		//cacheManager.getCache("ehcache1").clear();
		cacheManager.getCache("cache1").clear();	//ehcache使用这个时，如果没有对应的cache（例如cache1），则会报错
		cacheManager.getCache("cache2").clear();
		//但不能清除redis缓存
		
		return "success";
		//http://localhost:8888/tgc/removeKey
	}
	
	/**
	 * 给前台调用清除缓存的方法2  (对ehcache、redis等都可以用)
	 * 
	 * allEntries = true: 清空cache2里的所有缓存
     * 
     *  allEntries = true: 清空缓存book1里的所有值
    	allEntries = false: 默认值，此时只删除key对应的值
	 * 
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/clearCache2All")
	@CacheEvict(cacheNames={"cache1","cache2","cache3"}, allEntries=true)
	public String clearCache2All(String key) {
		//userService.clearCache2All();
		//使用@CacheEvict注解的方法必须是controller层直接调用，service里间接调用不生效
		return "success";
		//http://localhost:8888/tgc/clearCache2All
	}
	    
}
