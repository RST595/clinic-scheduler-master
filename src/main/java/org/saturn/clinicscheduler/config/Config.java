package org.saturn.clinicscheduler.config;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.saturn.clinicscheduler.repository",
        entityManagerFactoryRef = "emf", transactionManagerRef = "transManager")
@ComponentScan(value = "org.saturn.clinicscheduler")
@PropertySource({"classpath:application.properties", "classpath:exmessages.properties"})
public class Config implements WebMvcConfigurer {

    private final Environment env;
    @Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER;
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;
    @Value("${spring.liquibase.enabled}")
    private boolean isLiquibaseRun;

    @Autowired
    public Config(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        hibernateProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        hibernateProperties.put("hibernate.dialect",
                env.getProperty("spring.jpa.properties.hibernate.dialect"));
        hibernateProperties.put("hibernate.current_session_context_class",
                env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        return hibernateProperties;
    }

    @Bean(name = "emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.saturn.clinicscheduler.model");
        JpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean(name = "transManager")
    public PlatformTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return jpaTransactionManager;
    }

    @Bean
    public SpringLiquibase getSpringLiquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource());
        springLiquibase.setChangeLog(env.getProperty("spring.liquibase.change-log"));
        springLiquibase.setShouldRun(isLiquibaseRun);
        return springLiquibase;
    }
}