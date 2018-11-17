package com.tgc.config;
  
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
 
/**
 * 为指定的mapper包注入不同的sqlSessionTemplate sqlSessionFactory来绑定数据源
 * 给Mybatis配置数据源   使用test数据库
 * @author Administrator
 *
 */
@Configuration
@MapperScan(basePackages ={"com.tgc.mapper"}, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MybatisDbsZeroConfig {
    
   /*
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();	
    }
    */
    
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceZero") DataSource dataSource) throws Exception {
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
            @Qualifier("dataSourceZero") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
}
