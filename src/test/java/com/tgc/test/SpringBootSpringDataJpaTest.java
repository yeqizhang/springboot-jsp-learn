package com.tgc.test;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tgc.app.RunApp;
import com.tgc.dao.UserDao;
import com.tgc.entity.User;
 
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
         */
        @Test
        public void createUser()  {
            User newUser = new User();
            newUser.setName("test1");
            userDao.save(newUser);
            System.out.println(newUser.getId());
        }
         
}
