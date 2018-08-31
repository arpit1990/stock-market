package com.mavenhive.bootcamp.projects.stockmarket.main;

import com.mavenhive.bootcamp.projects.stockmarket.controllers.StockHistoryController;
import com.mavenhive.bootcamp.projects.stockmarket.controllers.UserAuthController;
import com.mavenhive.bootcamp.projects.stockmarket.exceptions.IncorrectUserCredentialsException;
import com.mavenhive.bootcamp.projects.stockmarket.exceptions.mapper.ApplicationExceptionsMapper;
import com.mavenhive.bootcamp.projects.stockmarket.exceptions.mapper.GenericExceptionMapper;
import com.mavenhive.bootcamp.projects.stockmarket.filters.CORSResponseFilter;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import java.util.logging.Logger;

@Configuration
@ApplicationPath("stock-market")
@ComponentScan(basePackages = "com.mavenhive.bootcamp.projects.stockmarket")
public class JerseyConfiguration extends ResourceConfig {
    private static final Logger log = Logger.getLogger(JerseyConfiguration.class.getName());

    public JerseyConfiguration() {
    }

    @PostConstruct
    public void setUp() {
        register(CORSResponseFilter.class);
        register(UserAuthController.class);
        register(StockHistoryController.class);
        //register(GenericExceptionMapper.class);
        //register(IncorrectUserCredentialsException.class);
        register(new LoggingFilter(log, true));
        ApplicationExceptionsMapper.registerExceptionMappers(this);
    }
}