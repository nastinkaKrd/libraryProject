package com.libraryProject.project.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PooledConnectionProvider implements ConnectionProvider{
    private static final PooledConnectionProvider instanse = new PooledConnectionProvider();
    private DataSource dataSource;
    public PooledConnectionProvider(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/libraryCard");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("nastia0403");
        dataSource = new HikariDataSource(hikariConfig);
    }

    public static PooledConnectionProvider getInstanse() {
        return instanse;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
