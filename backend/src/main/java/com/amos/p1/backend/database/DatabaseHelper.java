package com.amos.p1.backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private final DatabaseConfig databaseConfig;

    public DatabaseHelper(DatabaseConfig databaseConfig){
        this.databaseConfig = databaseConfig;
    }

    public void waitForDatabase(int pollingTimeInMs){
        while(!isDatabaseUp()){
            System.out.println("Database is with following information is not up: ");
            System.out.println(databaseConfig);
            System.out.println("Retry in " + pollingTimeInMs + " ms");

            try {
                Thread.sleep(pollingTimeInMs);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

        System.out.println("Database is with following information is up: ");
        System.out.println(databaseConfig);
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
