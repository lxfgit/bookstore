package com.book.lxf.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Created by LXF on 2015/11/18.
 */
public class JDBCUtils{

    private static DataSource dataSource = null;
    static {
        dataSource = new ComboPooledDataSource("book");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void releaseConnection(Connection connection) throws Exception {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库连接错误！");
        }
    }

    public static void release(ResultSet resultSet,Statement statement) throws Exception {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("数据库连接错误！");
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据库连接错误！");
        }
    }

}
