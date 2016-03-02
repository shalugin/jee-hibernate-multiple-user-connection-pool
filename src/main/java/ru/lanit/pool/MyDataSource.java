package ru.lanit.pool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import ru.lanit.RequestData;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.logging.Logger;

@Slf4j
@Singleton
@Startup
public class MyDataSource implements DataSource {

    @Inject
    private RequestData requestData;

    private ComboPooledDataSource cpds;

    public MyDataSource() throws PropertyVetoException {
        cpds = new ComboPooledDataSource();
//        cpds.setDriverClass("org.postgresql.Driver");
//        cpds.setJdbcUrl("jdbc:postgresql://localhost:15432/postgres");
//        cpds.setUser("ti");
//        cpds.setPassword("123");
        cpds.setDriverClass("org.h2.Driver");
        cpds.setJdbcUrl("jdbc:h2:mem:db1");
        cpds.setUser("sa");
        cpds.setPassword("");
        cpds.setMaxPoolSize(500);
        cpds.setMinPoolSize(10);
    }

    @PostConstruct
    public void postConstruct() {
        try {
            for (int i = 0; i < 500; i++) {
                try (Connection connection = cpds.getConnection()) {
                    Statement statement = connection.createStatement();
                    String sql = "CREATE USER u" + i + " PASSWORD 'u" + i + "'";
                    log.info("sql: {}", sql);
                    statement.execute(sql);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        log.info("getConnection(), {}", requestData);
        return cpds.getConnection(requestData.getLogin(), requestData.getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        log.info("getConnection({}, {})", username, password);
        return cpds.getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return cpds.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return cpds.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return cpds.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        cpds.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        cpds.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return cpds.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return cpds.getParentLogger();
    }

    void close() {
        log.info("close()");
        cpds.close();
    }
}