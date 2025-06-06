//package com.gigmoneytransferapi.spring.money_transfer;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//public class DataBaseConfig {
//
//    // This is to connect to a databse i created via PostGres CLI
//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:postgresql://localhost:5432/moneytransferdb")
//                .username("george.baldwin")
//                .password("")
//                .driverClassName("org.postgresql.Driver")
//                .build();
//    }
//
//    // I have had to add these in due to application.properties not being picked up on my IDE
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("com.gigmoneytransferapi.spring.money_transfer");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        Properties jpaProperties = new Properties();
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
//        jpaProperties.put("hibernate.show_sql", "true");
//
//        em.setJpaProperties(jpaProperties);
//
//        return em;
//    }
//}
