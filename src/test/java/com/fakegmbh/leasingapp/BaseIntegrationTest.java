package com.fakegmbh.leasingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(classes = LeasingAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    private static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    private static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    private static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    private static final MySQLContainer mySQLContainer;
    private static final String MYSQL_DOCKER = "mysql:8.0";
    private static final String MYSQL_USER = "user";
    private static final String MYSQL_PASSWORD = "password";

    @Autowired
    public TestRestTemplate restTemplate;

    static {
        mySQLContainer = (MySQLContainer)(new MySQLContainer(MYSQL_DOCKER)
                .withUsername(MYSQL_USER)
                .withPassword(MYSQL_PASSWORD)
                .withReuse(false));
        mySQLContainer.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add(SPRING_DATASOURCE_URL, mySQLContainer::getJdbcUrl);
        registry.add(SPRING_DATASOURCE_PASSWORD, mySQLContainer::getPassword);
        registry.add(SPRING_DATASOURCE_USERNAME, mySQLContainer::getUsername);
    }
}