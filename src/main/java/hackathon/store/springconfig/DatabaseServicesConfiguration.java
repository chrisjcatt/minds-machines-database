/*
 * Copyright (c) 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package hackathon.store.springconfig;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for the database connectivity.
 * 
 * @author 502604074
 *
 */
@Configuration
@EnableCaching
@EnableTransactionManagement
@EntityScan
public class DatabaseServicesConfiguration {

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws Exception {
        return new JpaTransactionManager(entityManagerFactory);
    }
}