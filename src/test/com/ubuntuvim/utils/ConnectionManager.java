package test.com.ubuntuvim.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ubuntuvim.utils.Config;

/**
 * 数据库链接配置，使用连接池
 * @author ubuntuvim
 * @email 1527254027@qq.com
 * @datatime 2015-7-23 下午10:12:48
 */
public class ConnectionManager {
	
	private static Properties props = null;
	public static ComboPooledDataSource dataSource;  
	
	static {  
	    try {
	    	props = new Properties();
	    	props.load(Config.class.getResourceAsStream("/dbconfig.properties"));
	    	
	        dataSource = new ComboPooledDataSource();  
	        dataSource.setUser(props.getProperty("userName"));  
	        dataSource.setPassword(props.getProperty("passWord"));  
	        dataSource.setJdbcUrl(props.getProperty("url"));  
	        dataSource.setDriverClass(props.getProperty("driverClass"));
	        dataSource.setInitialPoolSize(Integer.parseInt(props.getProperty("initialPoolSize")));  
	        dataSource.setMinPoolSize(Integer.parseInt(props.getProperty("minPoolSize")));  
	        dataSource.setMaxPoolSize(Integer.parseInt(props.getProperty("maxPoolSize")));  
	        dataSource.setMaxStatements(Integer.parseInt(props.getProperty("maxStatements")));  
	        dataSource.setMaxIdleTime(Integer.parseInt(props.getProperty("maxIdleTime")));  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}  
	  
	/** 
	 * 从连接池中获取数据源链接 
	 *  
	 * @author gaoxianglong 
	 *  
	 * @return Connection 数据源链接 
	 */  
	public static Connection getConnection() {  
	    Connection conn = null;  
	    if (null != dataSource) {  
	        try {  
	            conn = dataSource.getConnection();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return conn;  
	}  
}
