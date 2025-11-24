package com.hotking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionManager {

    private static BlockingQueue<Connection> pool;
    private static final String POOL_SIZE_KEY = "connection.pool.size";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";

    static {
        init();

        Runtime.getRuntime().addShutdownHook(new Thread(ConnectionManager::closeAllConnections));
    }

    private static void init(){
        try {
            Class.forName(PropertiesUtil.getProperty(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        int poolSize = Integer.parseInt(PropertiesUtil.getProperty(POOL_SIZE_KEY));
        pool = new ArrayBlockingQueue<>(poolSize);
        for(int i = 0; i < poolSize; i++){
            try {
                pool.add(DriverManager.getConnection(
                        PropertiesUtil.getProperty(URL_KEY),
                        PropertiesUtil.getProperty(USERNAME_KEY),
                        PropertiesUtil.getProperty(PASSWORD_KEY)
                ));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection get(){
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void returnConnection(Connection connection){
        pool.add(connection);
    }

    private static void closeAllConnections(){
        for (Connection connection : pool) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
