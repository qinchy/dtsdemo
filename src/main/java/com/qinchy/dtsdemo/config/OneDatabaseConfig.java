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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.qinchy.dtsdemo.one.mapper"}, sqlSessionFactoryRef = "oneSqlSessionTemplate")
public class OneDatabaseConfig {

    @Autowired
    public OneDataSourceProperties oneDataSourceProperties;

    @Primary
    @Bean(name = "oneDataSource", destroyMethod = "close")
    public DataSource oneDataSource() {
        DruidXADataSource dataSource = new DruidXADataSource();
        BeanUtils.copyProperties(oneDataSourceProperties, dataSource);
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(dataSource);
        xaDataSource.setUniqueResourceName("oneDataSource");
        return xaDataSource;
    }

    @Primary
    @Bean(name = "oneSqlSessionFactory")
    public SqlSessionFactory oneSqlSessionFactory(@Qualifier("oneDataSource") DataSource oneDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(oneDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:com/qinchy/dtsdemo/one/mapper/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "oneSqlSessionTemplate")
    public SqlSessionTemplate oneSqlSessionTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory oneSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(oneSqlSessionFactory);
    }
}