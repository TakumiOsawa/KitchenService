package com.ftgo.KitchenService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration of JPA.
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages= "com.ftgo.KitchenService.domain.ticket.repository")
public class JpaConfig {
    private final DataSource dataSource;

    /**
     * Constructor
     * @param dataSource dataSource
     */
    public JpaConfig(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Create JpaTransactionManager.
     * @return instance of JpaTransactionManager.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    /**
     * Create entityManagerFactory.
     * @return instance of LocalContainerEntityManagerFactoryBean.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan("com.ftgo.KitchenService.domain.ticket.entity");
        factory.setJpaProperties(properties);
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(dataSource);

        return factory;
    }
}