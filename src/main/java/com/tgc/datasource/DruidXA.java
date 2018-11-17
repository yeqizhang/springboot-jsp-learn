package com.tgc.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.xa.DruidXADataSource;

/**
 * 使用 DruidXADataSource 集成 Atomikos, 配置了三个数据源。
 * 
 * MysqlXA和 DruidXA 只能同时开启其中一种，想用哪种把另外那种的@Configuration注释
 * @author Administrator
 *
 */
@Configuration
public class DruidXA {
	
	   /* @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;*/

    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.druid.filters}")
    private String filters;

    @Value("{spring.datasource.druid.connectionProperties}")
    private String connectionProperties;
    
    @Value("{spring.datasource.druid.useGlobalDataSourceStat}")
    private String useGlobalDataSourceStat;
    
    @Bean(name = "dataSourceZero")
    public DataSource dataSource(DatabaseZeroProperties databaseZeroProperties) throws Exception{
    	
    	DruidXADataSource xaDataSource = new DruidXADataSource();	//DruidXADataSource继承javax.sql.XADataSource
        xaDataSource.setUrl(databaseZeroProperties.getUrl());
        xaDataSource.setUsername(databaseZeroProperties.getUsername());
        xaDataSource.setPassword(databaseZeroProperties.getPassword());
        //xaDataSource.setDriverClassName(databaseZeroProperties.getDriverClassName());
        
        setDruidXADataSourceProperties(xaDataSource);
        
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSource(xaDataSource);
        ds.setUniqueResourceName("dataSourceZero");
        
        //设置Atomikos属性
        ds.setMinPoolSize(databaseZeroProperties.getMinPoolSize());
        ds.setMaxPoolSize(databaseZeroProperties.getMaxPoolSize());
        ds.setBorrowConnectionTimeout(databaseZeroProperties.getBorrowConnectionTimeout());
		ds.setLoginTimeout(databaseZeroProperties.getLoginTimeout());
		ds.setMaintenanceInterval(databaseZeroProperties.getMaintenanceInterval());
		ds.setMaxIdleTime(databaseZeroProperties.getMaxIdleTime());
		ds.setTestQuery(databaseZeroProperties.getTestQuery());

        return ds;
    }	
	
    @Primary
    @Bean(name = "dataSourceOne")
    public DataSource dataSourceOne(DatabaseOneProperties databaseOneProperties) throws Exception {
    	
    	DruidXADataSource xaDataSource = new DruidXADataSource();	//DruidXADataSource继承javax.sql.XADataSource
        xaDataSource.setUrl(databaseOneProperties.getUrl());
        xaDataSource.setUsername(databaseOneProperties.getUsername());
        xaDataSource.setPassword(databaseOneProperties.getPassword());
        
        setDruidXADataSourceProperties(xaDataSource);
        
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSource(xaDataSource);
        ds.setUniqueResourceName("dataSourceOne");
        
        //设置Atomikos属性
        ds.setMinPoolSize(databaseOneProperties.getMinPoolSize());
        ds.setMaxPoolSize(databaseOneProperties.getMaxPoolSize());
        ds.setBorrowConnectionTimeout(databaseOneProperties.getBorrowConnectionTimeout());
		ds.setLoginTimeout(databaseOneProperties.getLoginTimeout());
		ds.setMaintenanceInterval(databaseOneProperties.getMaintenanceInterval());
		ds.setMaxIdleTime(databaseOneProperties.getMaxIdleTime());
		ds.setTestQuery(databaseOneProperties.getTestQuery());

        return ds;
    	
    }

    @Bean(name = "dataSourceTwo")
    public DataSource dataSourceTwo(DatabaseTwoProperties databaseTwoProperties) throws Exception {
//        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
//
//        atomikosDataSourceBean.setXaDataSourceClassName(databaseTwoProperties.getType());
//        Properties twoProperties = new Properties();
//
//        twoProperties.put("url", databaseTwoProperties.getUrl());
//        twoProperties.put("username", databaseTwoProperties.getUsername());
//        twoProperties.put("password", databaseTwoProperties.getPassword());
//        
//        twoProperties.put("initialSize", initialSize);
//        twoProperties.put("minIdle",minIdle );
//        twoProperties.put("maxActive",maxActive );
//        twoProperties.put("maxWait", maxWait);
//        twoProperties.put("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
//        twoProperties.put("minEvictableIdleTimeMillis",minEvictableIdleTimeMillis );
//        twoProperties.put("validationQuery",validationQuery );
//        twoProperties.put("testWhileIdle",testWhileIdle );
//        twoProperties.put("testOnBorrow", testOnBorrow);
//        twoProperties.put("testOnReturn",testOnReturn );
//        twoProperties.put("poolPreparedStatements",poolPreparedStatements);
//        twoProperties.put("maxPoolPreparedStatementPerConnectionSize",maxPoolPreparedStatementPerConnectionSize );
//        twoProperties.put("filters",filters);
//        twoProperties.put("connectionProperties",connectionProperties);
//        twoProperties.put("useGloalDataSourceStat",useGloalDataSourceStat);
//
//        atomikosDataSourceBean.setXaProperties(twoProperties);
//        atomikosDataSourceBean.setUniqueResourceName("dataSourceTwo");
//
//        return atomikosDataSourceBean;
    	
    	DruidXADataSource xaDataSource = new DruidXADataSource();	//DruidXADataSource继承javax.sql.XADataSource
        xaDataSource.setUrl(databaseTwoProperties.getUrl());
        xaDataSource.setUsername(databaseTwoProperties.getUsername());
        xaDataSource.setPassword(databaseTwoProperties.getPassword());
        
        setDruidXADataSourceProperties(xaDataSource);
        
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSource(xaDataSource);
        ds.setUniqueResourceName("dataSourceTwo");
        
        //设置Atomikos属性
        ds.setMinPoolSize(databaseTwoProperties.getMinPoolSize());
        ds.setMaxPoolSize(databaseTwoProperties.getMaxPoolSize());
        ds.setBorrowConnectionTimeout(databaseTwoProperties.getBorrowConnectionTimeout());
		ds.setLoginTimeout(databaseTwoProperties.getLoginTimeout());
		ds.setMaintenanceInterval(databaseTwoProperties.getMaintenanceInterval());
		ds.setMaxIdleTime(databaseTwoProperties.getMaxIdleTime());
		ds.setTestQuery(databaseTwoProperties.getTestQuery());

        return ds;
    }
    
    /**
     * 设置druid 除url username password 以外的属性
     * @param xaDataSource
     */
    private void setDruidXADataSourceProperties(DruidXADataSource xaDataSource){
    	xaDataSource.setInitialSize(initialSize);
        xaDataSource.setMinIdle(minIdle);
        xaDataSource.setMaxActive(maxActive);
        xaDataSource.setMaxWait(maxWait);
        xaDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        xaDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        xaDataSource.setValidationQuery(validationQuery);
        xaDataSource.setTestWhileIdle(testWhileIdle);
        xaDataSource.setTestOnBorrow(testOnBorrow);
        xaDataSource.setTestOnReturn(testOnReturn);
        xaDataSource.setPoolPreparedStatements(poolPreparedStatements);
        xaDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            xaDataSource.setFilters(filters);
        } catch (SQLException e) {
            //logger.info("druid configuration initialization filter", e);
        }
        xaDataSource.setConnectionProperties(connectionProperties);
        xaDataSource.setUseGlobalDataSourceStat(Boolean.valueOf(useGlobalDataSourceStat));
    }
}
