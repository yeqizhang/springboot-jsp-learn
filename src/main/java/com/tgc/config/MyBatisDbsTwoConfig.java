package com.tgc.config;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 为指定的mapper包注入不同的sqlSessionTemplate sqlSessionFactory来绑定数据源
 * @author Administrator
 *
 */
@Configuration
//basePackages 最好分开配置 如果放在同一个文件夹可能会报错
@MapperScan(basePackages = "com.tgc.db2", sqlSessionTemplateRef = "test2SqlSessionTemplate")
public class MyBatisDbsTwoConfig {

	@Bean(name = "test2SqlSessionFactory")
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSourceTwo") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "test2SqlSessionTemplate")
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/**
     * 初始化名为secondaryJdbcTemplate的JdbcTemplate对象的数据源为dataSourceTwo
     * @param dataSource
     * @return
     */
	 @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(
            @Qualifier("dataSourceTwo") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
	 
}
