package com.biswasakashdev.nexussphere.core.repository;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;

@DataR2dbcTest
@ActiveProfiles(value = {
        "test"
})
abstract class AbstractRepositoryTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * Truncates all the tables in the database
     */
    @AfterEach
    void afterEach() {
        databaseClient.sql("""
                        TRUNCATE TABLE
                            users_on_workspaces,
                            pages,
                            workspaces,
                            nex_users
                            RESTART IDENTITY CASCADE
                        """)
                .fetch()
                .rowsUpdated()
                .block();
    }

    void populateDB() {
        ResourceDatabasePopulator populator =
                new ResourceDatabasePopulator(
                        new ClassPathResource("db/data/data.sql")
                );

        populator.populate(connectionFactory)
                .block();
    }
}