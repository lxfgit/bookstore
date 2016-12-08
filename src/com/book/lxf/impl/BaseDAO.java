package com.book.lxf.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.book.lxf.dao.Dao;
import com.book.lxf.db.JDBCUtils;
import com.book.lxf.domain.ShoppingCartItem;
import com.book.lxf.utils.ReflectionUtils;
import com.book.lxf.web.ConnectionContext;


/**
 * Created by LXF on 2015/11/19.
 */
public class BaseDAO <T> implements Dao<T>{

    private QueryRunner queryRunner = new QueryRunner();

    private Class<T> tClass;

    public BaseDAO() {
        tClass = ReflectionUtils.getSuperGenericType(getClass());
    }

    @Override
    public long insert(String sql, Object... args) throws Exception {

        long id = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionContext.getInstance().get();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (args != null) {
                for (int i = 0 ; i < args.length ; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(resultSet,preparedStatement);
        }
        return id;
    }

    @Override
    public void update(String sql, Object... args) throws Exception {
        Connection connection = null;
        try {
            connection = ConnectionContext.getInstance().get();
            queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        } 

    }

    @Override
    public T query(String sql, Object... args) throws Exception {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<>(tClass), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return null;
    }

    @Override
    public List<T> queryForList(String sql, Object... args) throws Exception {
        Connection connection = null;
        try {
            connection = ConnectionContext.getInstance().get();
            return queryRunner.query(connection, sql, new BeanListHandler<>(tClass), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return null;
    }

    @Override
    public void batch(String sql, Object[]... params) throws Exception {
        Connection connection = null;
        try {
            connection = ConnectionContext.getInstance().get();
            queryRunner.batch(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        } 

    }

	@Override
    public <V> V getSingleVal(String sql, Object... args) throws Exception {
        Connection connection = null;
        try {
            connection = ConnectionContext.getInstance().get();
            return (V)queryRunner.query(connection, sql, new ScalarHandler(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		// TODO Auto-generated method stub
		
	}
}
