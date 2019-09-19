package com.tgc.datasource;

import javax.sql.DataSource;

import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * 使用MysqlXADataSource集成Atomikos, 配置了三个数据源。
 * 然后绑定到JdbcTemplate、Mybatis、SpringdataJpa进行数据库操作
 * 
 * dataSource 元素使用基本的 JDBC 数据源接口来配置 JDBC 连接对象的资源。
 * 
 *  * MysqlXA和 DruidXA 只能同时开启其中一种，想用哪种把另外那种的@Configuration注释
 * @author Administrator
 *
 */
//@Configuration
public class MysqlXA {
	/**
     * 使用Atomikos 的 DataSource实现
     * @param databaseZeroProperties
     * @return
	 * @throws Exception 
     */
	@Bean(name = "dataSourceZero")
    public DataSource dataSourceZero(DatabaseZeroProperties databaseZeroProperties) throws Exception {	  
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(databaseZeroProperties.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(databaseZeroProperties.getPassword());
		mysqlXaDataSource.setUser(databaseZeroProperties.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("dataSourceZero");
		
		xaDataSource.setMinPoolSize(databaseZeroProperties.getMinPoolSize());
		xaDataSource.setMaxPoolSize(databaseZeroProperties.getMaxPoolSize());
		xaDataSource.setBorrowConnectionTimeout(databaseZeroProperties.getBorrowConnectionTimeout());
		xaDataSource.setLoginTimeout(databaseZeroProperties.getLoginTimeout());
		xaDataSource.setMaintenanceInterval(databaseZeroProperties.getMaintenanceInterval());
		xaDataSource.setMaxIdleTime(databaseZeroProperties.getMaxIdleTime());
		xaDataSource.setTestQuery(databaseZeroProperties.getTestQuery());

		return xaDataSource;
    }
	
    @Primary
    @Bean(name = "dataSourceOne")
    public DataSource dataSourceOne(Environment environment, DatabaseOneProperties databaseOneProperties) throws Exception {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();

        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(databaseOneProperties.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUser(databaseOneProperties.getUsername());
        mysqlXADataSource.setPassword(databaseOneProperties.getPassword());

        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        atomikosDataSourceBean.setUniqueResourceName("dataSourceOne");
        
        atomikosDataSourceBean.setMinPoolSize(databaseOneProperties.getMinPoolSize());
        atomikosDataSourceBean.setMaxPoolSize(databaseOneProperties.getMaxPoolSize());
        atomikosDataSourceBean.setBorrowConnectionTimeout(databaseOneProperties.getBorrowConnectionTimeout());
        atomikosDataSourceBean.setLoginTimeout(databaseOneProperties.getLoginTimeout());
        atomikosDataSourceBean.setMaintenanceInterval(databaseOneProperties.getMaintenanceInterval());
        atomikosDataSourceBean.setMaxIdleTime(databaseOneProperties.getMaxIdleTime());
        atomikosDataSourceBean.setTestQuery(databaseOneProperties.getTestQuery());

        return atomikosDataSourceBean;
    }

    @Bean(name = "dataSourceTwo")
    public DataSource dataSourceTwo(DatabaseTwoProperties databaseTwoProperties) throws Exception {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();

        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(databaseTwoProperties.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setUser(databaseTwoProperties.getUsername());
        mysqlXADataSource.setPassword(databaseTwoProperties.getPassword());

        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        atomikosDataSourceBean.setUniqueResourceName("dataSourceTwo");
        
        atomikosDataSourceBean.setMinPoolSize(databaseTwoProperties.getMinPoolSize());
        atomikosDataSourceBean.setMaxPoolSize(databaseTwoProperties.getMaxPoolSize());
        atomikosDataSourceBean.setBorrowConnectionTimeout(databaseTwoProperties.getBorrowConnectionTimeout());
        atomikosDataSourceBean.setLoginTimeout(databaseTwoProperties.getLoginTimeout());
        atomikosDataSourceBean.setMaintenanceInterval(databaseTwoProperties.getMaintenanceInterval());
        atomikosDataSourceBean.setMaxIdleTime(databaseTwoProperties.getMaxIdleTime());
        atomikosDataSourceBean.setTestQuery(databaseTwoProperties.getTestQuery());

        return atomikosDataSourceBean;
    }


}
