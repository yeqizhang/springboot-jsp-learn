package com.tgc.test;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tgc.app.RunApp;
import com.tgc.dao.UserDao;
import com.tgc.entity.User;

/**
 *JUnit中的注解
	@BeforeClass：针对所有测试，只执行一次，且必须为static void
	@Before：初始化方法，执行当前测试类的每个测试方法前执行。
	@Test：测试方法，在这里可以测试期望异常和超时时间
	@After：释放资源，执行当前测试类的每个测试方法后执行
	@AfterClass：针对所有测试，只执行一次，且必须为static void
	@Ignore：忽略的测试方法（只在测试类的时候生效，单独执行该测试方法无效）
	@RunWith:可以更改测试运行器 ，缺省值 org.junit.runner.Runner
	
	一个单元测试类执行顺序为：
	@BeforeClass –> @Before –> @Test –> @After –> @AfterClass
	
	每一个测试方法的调用顺序为：
	@Before –> @Test –> @After
 * 
 * 光标放到某个方法上，则只会测试这个方法，放到类名上则会测试这个类的所有@Test方法。
 *
 */
//SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
//指定我们SpringBoot工程的Application启动类
@SpringApplicationConfiguration(classes = RunApp.class)
public class SpringBootSpringDataJpaTest {
      
        @Autowired
        private UserDao userDao;
        
        @Before
        public void setUp() throws Exception {
        	System.out.println("每个方法前===");
        }

        @After
        public void tearDown() throws Exception {
        	System.out.println("每个方法后===");
        }
 
 
        @Test
        public void findallUsers()  {
        	List<User> list = userDao.findAll();
        	System.out.println(list.get(0));
        }
        
        /**
         * 测试可通过。  但打印null。   实际不会插入到数据库。  
         * http://localhost:8888/tgc/createUserBySpringDataJpa  测试可插入数据库，没有问题
         *在junit下，插入数据会自动回滚，所以测试显示成功但实际上不能插入。若要插入到数据库，只需在测试方法上添加@Rollback(false)注解即可。
         */
        @Test
        public void createUser()  {
            User newUser = new User();
            newUser.setName("test1");
            userDao.save(newUser);
            System.out.println(newUser.getId());
        }
        
        /*
         * 超时测试  （200毫秒时测试不通过，改成1000毫秒时通过。通过时看到打印时间为500毫秒多，不过测试失败是不会打印这个时间的。）
         */
        @Test(timeout = 1000)
        public void testTimeout() throws InterruptedException {
            //TimeUnit.SECONDS.sleep(2);
        	long startTime = System.currentTimeMillis();    //获取开始时间
        	List<User> list = userDao.findAll();
        	long endTime = System.currentTimeMillis();    //获取结束时间
        	System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
            System.out.println("Complete");
        }
        
        // 执行测试时将忽略掉此方法，如果用于修饰类，则忽略整个类
        @Ignore("not ready yet")
        @Test
        public void testIgnore()  {
            System.out.println("不会执行此方法");
        }
}
