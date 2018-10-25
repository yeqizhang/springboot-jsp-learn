 package com.tgc.datasource;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * 整合多数据源SpringDataJpa的配置    （事务管理器为Atomikos）
 * @author Administrator
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactorySecondary",
basePackages= { "com.tgc.dao"})
@EntityScan("com.tgc.entity")
public class SpringDataJpaConfig {
	
	/*@Autowired
	private JpaProperties jpaProperties;*/

    /**
     * 使用Atomikos 的 DataSource实现     dbConfigInfo为test数据库配置对象 
     * @param dbConfigInfo
     * @return
     */
	@Bean(name = "myDataSource")
    public DataSource dataSource2(DbConfigInfo dbConfigInfo) {	  
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(dbConfigInfo.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(dbConfigInfo.getPassword());
		mysqlXaDataSource.setUser(dbConfigInfo.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("myDataSource");

		return xaDataSource;
    }
 
 
    @Bean(name = "entityManagerSecondary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder,@Qualifier("myDataSource") DataSource dataSource) {
        return entityManagerFactorySecondary(builder,dataSource).getObject().createEntityManager();
    }
 
    /**
     * 此处也要传入@Qualifier("myDataSource")   不然会使用@Primary的数据源
     * @param builder
     * @param dataSource
     * @return
     */
    @Bean(name = "entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary (EntityManagerFactoryBuilder builder,@Qualifier("myDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                //.properties(getVendorProperties(dataSource))
                //.packages("com.tgc.dao","com.tgc.entity")
                //.persistenceUnit("secondaryPersistenceUnit")
                .build();
    }
    
    /*
    private Map<String, String> getVendorProperties(DataSource dataSource) {
    	return jpaProperties.getHibernateProperties(dataSource);
    }
    */
 
    /*
    @Bean(name = "transactionManagerSecondary")
    PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
    }
    */
 
}