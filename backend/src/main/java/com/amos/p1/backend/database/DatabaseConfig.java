package com.amos.p1.backend.database;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringJoiner;

public class DatabaseConfig {

    private final String databaseName = "testdb3";
    private final String user = "root";
    private final String password = "root";
    private final String jdbcDriver = "com.mysql.cj.jdbc.Driver";

    public String getURL() {
        return "jdbc:mysql://localhost:3306";
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }


    /**
     * Return the ip adress of the host. Example: 192.168.0.183
     */
    private String getHostAdress() {
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", DatabaseConfig.class.getSimpleName() + "[", "]")
                .add("url='" + getURL() + "'")
                .add("databaseName='" + databaseName + "'")
                .add("user='" + user + "'")
                .add("password='" + password + "'")
                .add("jdbcDriver='" + jdbcDriver + "'")
                .toString();
    }
}
