package com.ems.webapp.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BaseDao {
	private static final Logger logger = LogManager.getLogger(BaseDao.class.getName());

	private static String[] loadConfig() {
		/*
		 * Load MySQL config from properties file check
		 * /src/main/resources/mysqlconn.properties to set properties
		 */
		Properties props = new Properties();
		try {
			InputStream stream = BaseDao.class.getResourceAsStream("/mysqlconn.properties");
			props.load(stream);
			String url = props.getProperty("db.conn.url");
			String uname = props.getProperty("db.username");
			String pwd = props.getProperty("db.password");

			Class.forName("com.mysql.jdbc.Driver");
			return new String[] { url, uname, pwd };
		} catch (Exception e) {
			logger.error(e);
			return new String[] { "", "", "" };
		}
	}

	public static Connection getConnection() throws SQLException {
		// load the config from file and inits a cpnnection to MySQL server
		String[] jdbcConfig = loadConfig();
		return DriverManager.getConnection(jdbcConfig[0], jdbcConfig[1], jdbcConfig[2]);
	}

}
