数据库在data/sql下
主要实现功能有：
一、配置相关：
 1 注解的使用。 
 2 使用yml配置文件。
 3 pom依赖配置，打包配置。   起步依赖.
 4 （多环境配置）在主配置文件中指定 开发环境、生产环境、测试环境 的配置文件。
 5 使用@Value注入配置文件的配置。 
 6 配置对象初始化注册. @EnableConfigurationProperties
 7 端口以及context-path配置.   

二、异常捕获、web视图、静态资源访问（图片等）、log4j日志管理、jsp使用el表达式以及jstl
 1 全局捕获异常，不返回原始错误信息、返回指定内容。
 2 使用freemarker模板引擎 （此工程因使用jsp而没有使用）、使用jsp页面（springboot官方不推荐，需要使用war打包）。
 3 静态资源目录名 规则：
 4 log4j，引入依赖和配置文件后后，直接可以使用。  

三、使用jdbctemplate、spring-data-jpa、整合mybatis、多数据源
四、使用分布式事务解决方案  Atomikos （多数据源） 
五、定时任务（使用注解）、缓存服务（ehcache。xml 配置，app中激活）、AOP（引入依赖，使用注解）、异步方法（引入依赖，使用注解）
六、打包部署方法
七、Junit测试
---------------------------------------------------------------------------------------------  

一、配置相关：
 1 注解的使用。 
  @ComponentScan @MapperScan @SpringBootApplication 
  @Controller @RestController @ResponseBody 
  @Configuration @Component
  
  @EnableAutoConfiguration注解:作用在于让 Spring Boot根据应用所声明的依赖来对 Spring 框架进行自动配置，
  这个注解告诉Spring Boot根据添加的jar依赖猜测你想如何配置Spring。由于spring-boot-starter-web添加了Tomcat和Spring MVC，所以auto-configuration将假定你正在开发一个web应用并相应地对Spring进行设置。
 2 使用yml配置文件。
  ym可以树形（不会重复spring.datasource.username、spring.datasource.password这样的重复spring.datasource）
 3 pom依赖配置，打包配置。   起步依赖.
   <!-- 引入SpringBoot父类依赖  -->
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.3.3.RELEASE</version>
	</parent>
	<!-- SpringBoot 核心组件 -->
	<!--（起步依赖） 1.3.3 从 spring-boot-starterparent继承版本号-->
	<!-- springboot-web组件 springmvc+spring+mybatis -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
 4 （多环境配置）在主配置文件中指定 开发环境、生产环境、测试环境 的配置文件。
  spring.profiles.active=prod
 5 使用@Value注入配置文件的配置。 
  @Value("${my.name}")	//取配置文件中的配置
  private String myname;
 6 配置对象初始化注册. @EnableConfigurationProperties
 7 端口以及context-path配置.   

二、异常捕获、web视图、静态资源访问（图片等）、log4j日志管理、jsp使用el表达式以及jstl
 1 全局捕获异常，不返回原始错误信息、返回指定内容。
  @ControllerAdvice
 2 使用freemarker模板引擎 （此工程因使用jsp而没有使用）、使用jsp页面（springboot官方不推荐，需要使用war打包）。
          引入依赖即可。可自定义路径以及文件后缀<!-- 引入freeMarker的依赖包. -->
		<dependency>
		<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-freemarker</artifactId>
		</dependency>
   jsp文件在 src\main\webapp下。
          引入jsp依赖后可直接使用el表达式，但使用jstl还需要依赖：
    	<dependency>
		   <groupId>javax.servlet</groupId>
		   <artifactId>jstl</artifactId>
		</dependency>
 3 静态资源目录名 规则：
	/static
	/public
	/resources	
	/META-INF/resources 
        例如：图片放在static文件夹内，直接浏览器访问  http://localhost:8888/tgc/d.jpg
 4 log4j，引入依赖和配置文件后后，直接可以使用。  

三、使用jdbctemplate、spring-data-jpa、整合mybatis、多数据源
 1 使用jdbctemplate 
         引入jdbcTemplate的依赖包
         <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
   @Autowired
   private JdbcTemplate jdbcTemplate;
 2 spring-data-jpa
 	JPA默认使用Hibernate作为ORM实现，所以，一般使用Spring Data JPA即会使用Hibernate。
   <!--spring-boot-starter-data-jpa 包含spring-data-jpa、spring-orm 和 Hibernate 来支持 JPA-->
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-data-jpa</artifactId>
	</dependency>  
	另外：mybatis不是orm框架。
	整合进多数据源分布式事务管理的配置为：  javax.sql.DataSource.SpringDataJpaConfig
 3 mybatis	
         引入mybatis依赖。写mapper类，然后启动类加@MapperScan 
 4 多数据源 
 	@Configuration 
 	@MapperScan(basePackages = "", sqlSessionFactoryRef = "")
 	basePackages该包下使用此sqlSessionFactoryRef数据源
四、使用分布式事务解决方案  Atomikos （多数据源） 
       引入Atomikos依赖，创建DataSource的实现AtomikosDataSourceBean对象即可。（创建两个Atomikos数据源，Atomikos容器能解决事务处理问题）
       注意：Atomikos的数据源配置中不要实现 TransactionManager方法，其它的数据源比如mybatis数据源则需要重新写数据源配置，目前采用也加入到Atomikos容
       器下管理数据源。
     以下的解决方案待参考。    
       问题参考： https://blog.csdn.net/u011493599/article/details/66973138
       文章为 doc/多数据源分布式事物管理（事务管理器）.txt    
           
五、定时任务（使用注解）、缓存服务（ehcache。xml 配置，app中激活）、AOP（引入依赖，使用注解）、异步方法（引入依赖，使用注解）
  1 定时任务  直接使用@Scheduled注解类方法，类使用@Component组件
  2 缓存服务 
             引用spring-boot-starter-cache，给mapper注解@CacheConfig、方法上使用@Cacheable，配置ehcache.xml，
             清除缓存：cacheManager.getCache("baseCache").clear(); 
     @Autowired
	 private CacheManager cacheManager;
  3 AOP 
           依赖spring-boot-starter-aop，   使用@Aspect@Component注解切面类
  4 异步方法 使用@Async注解。 
六、打包部署方法：
Eclipse中运行mvn package 命令打包（右键项目-show in-Terminal ,电脑的那个图标是cmd命令）  （此方式没通过，采用下面的解决方案右击项目→Run As→Run Configurations→Maven Bulid→New）
使用命令行java -jar 包名 来启动运行。
譬如 java -jar springboot-jsp-learn-0.0.1-SNAPSHOT.war 
java -jar springboot-jsp-learn-0.0.1-SNAPSHOT.jar

target目录下输出 编译文件 jar war
遇到以下问题：
**************************解决:javac: 无效的目标发行版: 1.8******************************
今天用cmd命令maven打包 
因为本地装了多个jdk造成环境混乱 打包报错：javac: 无效的目标发行版: 1.8

用网上方法未解决该问题

最后使用eclipse maven打包

右击项目→Run As→Run Configurations→Maven Bulid→New

Name 自定义 
Base directory D:\workspace\mavenstarter\mavenstarter（项目所在文件夹） 
Goals：clean package

Apply→Run

打包成功 war包在 target目录下。
*********************************************************************************
…………………………………………………………………………打包方式为jar 访问jsp为404的解决方法………………………………………………………………………………………………………

<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <!--目前只能使用1.4.2版本，使用其他版本jsp无法显示-->
                <version>1.4.2.RELEASE</version>
                <configuration>
                    <fork>true</fork>
                    <!-- spring-boot:run 中文乱码解决 -->
                    <jvmArguments>-Dfile.encoding=UTF-8</jvmArguments>
                    <mainClass>com.zyc.zspringboot.ZspringbootApplication</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <configuration>
                    <failOnMissingWebXml>false</failOnMissingWebXml>
                </configuration>
            </plugin>
        </plugins>
 
        <resources>
            <!-- 打包时将jsp文件拷贝到了META-INF目录下    （解压springboot-jsp-learn-0.0.1-SNAPSHOT.jar可知）-->
            <resource>
                <!-- 指定resources插件处理哪个目录下的资源文件 -->
                <directory>src/main/webapp</directory>
                <!--拷贝到此目录下才能被访问到-->
                <targetPath>META-INF/resources</targetPath>
                <includes>
                    <include>**/**</include>
                </includes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/**</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <excludes>
                    <exclude>**/*.java</exclude>
                </excludes>
            </resource>
        </resources>
    </build>
--------------------- 
作者：啊大海全是水 
来源：CSDN 
原文：https://blog.csdn.net/zhaoyachao123/article/details/79722519 
版权声明：本文为博主原创文章，转载请附上博文链接！
………………………………………………………………………………………………………………………………………………………………………………………………………………………………………………

七、Junit测试
@RunWith(SpringJUnit4ClassRunner.class) //SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = RunApp.class) //指定我们SpringBoot工程的Application启动类


