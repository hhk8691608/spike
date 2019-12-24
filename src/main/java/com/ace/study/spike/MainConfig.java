package com.ace.study.spike;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@MapperScan(basePackages = "com.ace.study.spike.mapper")
//@ComponentScan(basePackages = {"com.ace.study.spike.*"})

@Configuration
@MapperScan(basePackages = "com.ace.study.spike.mapper", annotationClass = Repository.class)
@EnableTransactionManagement // 一定要开启 支持@Transactional注解
public class MainConfig {

    /**
     * 创建数据源
     *
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "dataSource")
    @Primary
    public DataSource createDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 创建事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean("transactionManager")
    public PlatformTransactionManager createTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
