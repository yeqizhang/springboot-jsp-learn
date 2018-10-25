package com.tgc.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * 集成Atomikos分布式事务管理
 * @author Administrator
 *
 *SqlSessionFactoryBuilder：build方法创建SqlSessionFactory实例。
 * SqlSessionFactory：创建SqlSession实例的工厂。
 * SqlSession：用于执行持久化操作的对象，类似于jdbc中的Connection。
 * SqlSessionTemplate：MyBatis提供的持久层访问模板化的工具，线程安全，可通过构造参数或依赖注入SqlSessionFactory实例
 * 
 * 库1的数据源模板，应用在从库所对应的Dao层上（扫描对应的mapper），实现数据源1的指定+增删改查
 */
@Configuration
//basePackages 最好分开配置 如果放在同一个文件夹可能会报错
@MapperScan(basePackages = "com.tgc.db1", sqlSessionTemplateRef = "testSqlSessionTemplate")
public class MyBatisConfig1 {

	// 配置数据源
	@Bean(name = "testDataSource")
	@Primary
	public DataSource testDataSource(DBConfig1 testConfig) throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(testConfig.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(testConfig.getPassword());
		mysqlXaDataSource.setUser(testConfig.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		//使用Atomikos容器进行管理。
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("testDataSource");

		xaDataSource.setMinPoolSize(testConfig.getMinPoolSize());
		xaDataSource.setMaxPoolSize(testConfig.getMaxPoolSize());
		xaDataSource.setMaxLifetime(testConfig.getMaxLifetime());
		xaDataSource.setBorrowConnectionTimeout(testConfig.getBorrowConnectionTimeout());
		xaDataSource.setLoginTimeout(testConfig.getLoginTimeout());
		xaDataSource.setMaintenanceInterval(testConfig.getMaintenanceInterval());
		xaDataSource.setMaxIdleTime(testConfig.getMaxIdleTime());
		xaDataSource.setTestQuery(testConfig.getTestQuery());
		return xaDataSource;
	}

	 /**
     * 创建数据源
     * SqlSession：应用程序和数据库之间交互的一个单线程对象（非线程安全的），数据库的C、R、U、D及事务的处理接口，select | update | delete | insert | commit | rollback | close | flushStatements等
		SqlSessionFactory:工厂设计模式，创建SqlSession的工厂。SqlSessionFactory是MyBatis的关键对象
     * @param dataSource
     * @return
     */
	@Bean(name = "testSqlSessionFactory")
	@Primary
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("testDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "testSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("testSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
