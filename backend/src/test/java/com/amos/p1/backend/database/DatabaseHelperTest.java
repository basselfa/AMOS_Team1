package com.amos.p1.backend.database;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseHelperTest {

    private final DatabaseHelper databaseHelper;

    public DatabaseHelperTest(){
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseHelper = new DatabaseHelper(databaseConfig);
    }

    @Test
    void testWaitForDatabase() {
        // Start db while testing
        databaseHelper.waitForDatabase(5000);
    }

    @Test
    void testIsDatabaseUp() {
        MatcherAssert.assertThat(databaseHelper.isDatabaseUp(), equalTo(true));
    }
}