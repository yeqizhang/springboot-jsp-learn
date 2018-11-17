package com.tgc.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 将Properties的配置转换成java对象
 * @author tgc
 *
 */
@ConfigurationProperties(prefix = "spring.datasource.one")
public class DatabaseOneProperties extends DatabaseProperties {

}
