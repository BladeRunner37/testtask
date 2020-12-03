package ru.bladerunner37.testtask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "ru.bladerunner37.testtask.dao",
        entityManagerFactoryRef = "entityManagerFactoryTestDB",
        transactionManagerRef = "transactionManagerTestDB"
)
public class DbConfig {

    @Bean(name = "jpaPropertiesTestDB")
    @ConfigurationProperties(prefix = "datasource.testdb.jpa.properties")
    public Properties jpaProperties() {
        return new Properties();
    }

    @Bean(name = "dataSourceTestDB")
    @ConfigurationProperties(prefix = "datasource.testdb")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "transactionManagerTestDB")
    public PlatformTransactionManager transactionManagerTestDB(EntityManagerFactory entityManagerFactoryTestDB) {
        return new JpaTransactionManager(entityManagerFactoryTestDB);
    }

    @Bean(name = "entityManagerFactoryTestDB")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryTestDB(Properties jpaPropertiesTestDB,
                                                                             DataSource dataSourceTestDB) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSourceTestDB);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        Properties props = new Properties();
        props.putAll(jpaPropertiesTestDB);
        props.put("hibernate.jdbc.batch_size", 25);
        factoryBean.setJpaProperties(props);
        factoryBean.setPackagesToScan("ru.bladerunner37.testtask.entity");
        factoryBean.setPersistenceUnitName("TestDB");
        return factoryBean;
    }
}
