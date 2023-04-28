//package com.example.paymentsv2.robert
//
//import jakarta.persistence.EntityManagerFactory
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.autoconfigure.domain.EntityScan
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.orm.jpa.JpaTransactionManager
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
//import org.springframework.transaction.PlatformTransactionManager
//import org.springframework.transaction.annotation.EnableTransactionManagement
//import java.util.*
//import javax.sql.DataSource
//
//
//@Configuration
//@EnableTransactionManagement
//@EntityScan("com.example.paymentsv2.models")
////@EnableJpaRepositories(basePackages = ["com.example.paymentsv2.repositories"])
//class JpaConfiguration(@Autowired val dataSource: DataSource) {
//
//    @Bean
//    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
//        val em = LocalContainerEntityManagerFactoryBean()
//        em.dataSource = dataSource
//        em.setPackagesToScan("com.example.paymentsv2.models")
//        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
//        em.setJpaProperties(additionalProperties())
//        return em
//    }
//
//    @Bean
//    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
//        val transactionManager = JpaTransactionManager()
//        transactionManager.entityManagerFactory = entityManagerFactory
//        return transactionManager
//    }
//
//    private fun additionalProperties(): Properties {
//        val hibernateProperties = Properties()
//        //hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
//        hibernateProperties.setProperty("hibernate.show_sql", "true")
//        hibernateProperties.setProperty("hibernate.format_sql", "true")
//        return hibernateProperties
//    }
//}
