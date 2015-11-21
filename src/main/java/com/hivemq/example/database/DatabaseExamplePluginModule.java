package com.hivemq.example.database;

import com.hivemq.example.database.configuration.Configuration;

import com.google.inject.Provides;
import com.hivemq.spi.HiveMQPluginModule;
import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.plugin.meta.Information;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Dominik Obermaier
 */
@Information(
        name = "Database Example Plugin",
        version = "3.0.1",
        author = "dc-square GmbH",
        description = "A example plugin which persists every message to the database and authenticates clients from the database")
public class DatabaseExamplePluginModule extends HiveMQPluginModule {


    @Provides
    public Connection provideConnection(final HikariDataSource ds) throws SQLException {
        return ds.getConnection();
    }

    @Provides
    @Singleton
    public HikariDataSource provideConnectionPool() {

        final Configuration dbConfig = new Configuration();

        //See https://github.com/brettwooldridge/HikariCP

        final HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(15);
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", dbConfig.get("database.host"));
        config.addDataSourceProperty("port", dbConfig.get("database.port"));
        config.addDataSourceProperty("databaseName", dbConfig.get("database.name"));
        config.addDataSourceProperty("user", dbConfig.get("database.username"));
        config.addDataSourceProperty("password", dbConfig.get("database.password"));

        //See https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration

        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("useServerPrepStmts", true);

        return new HikariDataSource(config);

    }

    @Override
    protected void configurePlugin() {
    }

    @Override
    protected Class<? extends PluginEntryPoint> entryPointClass() {
        return DatabaseExamplePlugin.class;
    }
}