package com.imdb.finalproject.service.dbutil;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {

    private final static BasicDataSource ds = new BasicDataSource();
    private static Connection conn;

    private DBUtil(){}

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/imdb?serverTimeZone=UTC");
        ds.setUsername("Alex1990");
        ds.setPassword("Alex1990");
        ds.setInitialSize(8);
        ds.setMaxTotal(32);
        ds.setMinIdle(8);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return ds.getConnection();
    }

    public static void closeConnection(Connection c){
        try{
            if(c != null) c.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
