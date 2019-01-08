package com.qinchy.dtsdemo.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.qinchy.dtsdemo.two.mapper", sqlSessionFactoryRef = "twoSqlSessionTemplate")
public class TwoDatabaseConfig {

    @Autowired
    public TwoDataSourceProperties twoDataSourceProperties;

    @Bean(name = "twoDataSource", destroyMethod = "close")
    public DataSource twoDataSource() {
        DruidXADataSource dataSource = new DruidXADataSource();
        BeanUtils.copyProperties(twoDataSourceProperties, dataSource);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(dataSource);
        xaDataSource.setUniqueResourceName("twoDataSource");
        return xaDataSource;
    }

    @Bean(name = "twoSqlSessionFactory")
    public SqlSessionFactory twoSqlSessionFactory(@Qualifier("twoDataSource") DataSource twoDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(twoDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:com/qinchy/dtsdemo/two/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "twoSqlSessionTemplate")
    public SqlSessionTemplate twoSqlSessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory twoSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(twoSqlSessionFactory);
    }
}