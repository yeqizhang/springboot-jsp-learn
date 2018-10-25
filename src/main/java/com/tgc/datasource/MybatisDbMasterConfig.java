package com.tgc.datasource;
  
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
 
/**
 * 给Mybatis配置数据源  (使用这个会导致Atomikos无法事务管理??)
 * @author Administrator
 *
 */
@Configuration
@MapperScan(basePackages ="com.tgc.mapper", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MybatisDbMasterConfig {
	
    
   /*
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();	
    }
    */
    
    /**
     * 使用Atomikos 的 DataSource实现
     * @param dbConfigInfo
     * @return
     */
	@Bean(name = "masterDataSource")
    public DataSource dataSource2(DbConfigInfo dbConfigInfo) {	  
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(dbConfigInfo.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(dbConfigInfo.getPassword());
		mysqlXaDataSource.setUser(dbConfigInfo.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("masterDataSource");

		return xaDataSource;
    }
  
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //绑定entity和mapper映射？
       /*
        factoryBean.setTypeAliasesPackage("com.pactera.scm.entity");
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        */
        return factoryBean.getObject();
    }
  
    /**
     * 事务管理器，启用此方法会导致Atomikos不能事务管理  
     * (直接注释掉虽然Atomikos能够事务管理了，但是mybatis本身不能够事务管理了，所以上面不能使用DataSourceBuilder.create().build())
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    /*
    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    */
  
    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    /**
     * 初始化名为primaryJdbcTemplate的JdbcTemplate对象的数据源为masterDataSource 
     * masterDataSource由DataSourceBuilder.create().build()构建。
     * 
     * @param dataSource
     * @return
     */
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("masterDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
}
