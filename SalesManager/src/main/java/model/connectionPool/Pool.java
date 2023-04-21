package model.connectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * コネクションプーリングを初期化し起動させる
 * @author yuta
 *
 */
public class Pool {

private static DataSource dataSource;
	/**
	 * 起動method
	 * @throws IOException error
	 * @throws SQLException error
	 */
	public static void initialize() throws IOException, SQLException {
        Properties props = new Properties();
        InputStream is = Pool.class.getClassLoader().getResourceAsStream("pool.properties");
        props.load(is);

        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(props.getProperty("jdbc.url"));
        mysqlDS.setUser(props.getProperty("jdbc.username"));
        mysqlDS.setPassword(props.getProperty("jdbc.password"));

        dataSource = mysqlDS;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() throws SQLException {
        // MySQL DataSourceはclose()メソッドを持たないため、何も行いません。
    }
}
