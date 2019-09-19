 package com.tgc.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 整合多数据源SpringDataJpa的配置    （事务管理器为Atomikos）    使用test数据库
 * @author Administrator
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactorySecondary",basePackages= { "com.tgc.dao"})
//注意： 不配置transactionManager，事务管理采取数据源的。
@EntityScan("com.tgc.entity")
public class SpringDataJpaConfig {
	
	
	@Autowired @Qualifier("dataSourceZero")
	private DataSource secondaryDataSource;
	
	@Bean(name = "entityManagerSecondary")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
	    return entityManagerFactorySecondary(builder).getObject().createEntityManager();
	}
	
	@Bean(name = "entityManagerFactorySecondary")
	public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary (EntityManagerFactoryBuilder builder) {
	      return builder
	             .dataSource(secondaryDataSource)
//	             .properties(getVendorProperties(secondaryDataSource))
//	            .packages("springboot.domain.s") //设置实体类所在位置
//	            .persistenceUnit("secondaryPersistenceUnit")
	            .build();
	}

}