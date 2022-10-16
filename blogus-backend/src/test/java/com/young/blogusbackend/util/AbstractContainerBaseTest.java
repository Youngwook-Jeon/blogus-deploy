package com.young.blogusbackend.util;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class AbstractContainerBaseTest {

    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres"));
        POSTGRE_SQL_CONTAINER.start();
    }
}
