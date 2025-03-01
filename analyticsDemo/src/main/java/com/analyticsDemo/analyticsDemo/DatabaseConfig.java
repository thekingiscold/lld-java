package com.analyticsDemo.analyticsDemo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseConfig {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void testDataBaseConnection(){
        try(Connection connection = dataSource.getConnection()){
            System.out.println("Database connection successful. URL: "+connection.getMetaData().getURL());
        }catch (Exception e){
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}
