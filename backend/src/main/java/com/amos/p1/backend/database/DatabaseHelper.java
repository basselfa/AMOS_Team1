package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final Logger log = LoggerFactory.getLogger(DatabaseHelper.class);

    private final DatabaseConfig databaseConfig;

    public DatabaseHelper(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    public void waitForDatabase(int pollingTimeInMs){
        while(!isDatabaseUp()){
            log.info("Database is with following information is not up: ");
            log.info("" + databaseConfig);
            log.info("Retry in " + pollingTimeInMs + " ms");

            try {
                Thread.sleep(pollingTimeInMs);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

        log.info("Database is with following information is up: ");
        log.info("" + databaseConfig);
    }

    public boolean isDatabaseUp(){
        Connection con = null;
        ResultSet rs = null;

        try{
            Class.forName(databaseConfig.getJdbcDriver());

            con = DriverManager.getConnection(
                    databaseConfig.getURL(),
                    databaseConfig.getUser(),
                    databaseConfig.getPassword()
            );

            String dbName = databaseConfig.getDatabaseName();

            if(con != null){
                rs = con.getMetaData().getCatalogs();

                while(rs.next()){
                    String catalogs = rs.getString(1);

                    if(dbName.equals(catalogs)){
                        return true;
                    }
                }

                return false;

            } else{
                return false;

            }
        } catch(Exception ex){
            return false;
        } finally {
            if( rs != null){
                try{
                    rs.close();
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            if( con != null){
                try{
                    con.close();
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

}
