package com.ubuntuvim.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubuntuvim.utils.DBConnUtils;
import com.ubuntuvim.utils.Utils;

/**
 * 使用要求实体类（model）中的属性名一定要和数据库中的字段名一定要严格相同（包括大小写）,
 * 
 * @author ubuntuvim
 * @email 1527254027@qq.com
 * @datatime 2015-7-23 下午11:21:14
 * @param <T>
 */
public class CoreDao<T> {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private Class<T> clazz;
	// 定义数据库的链接
	private Connection connection;
	// 定义sql语句的执行对象
	private PreparedStatement pstmt;
	// 定义查询返回的结果集合
	private ResultSet resultSet;

	/**
	 * 子类初始时根据子类的泛型参数决定model的类型,这个构造函数不能直接调用
	 */
	@SuppressWarnings("unchecked")
	protected CoreDao() {
		this.clazz = (Class<T>) ClassGenericsUtil.getGenericClass(getClass());
	}

	/**
	 * 使用反射机制获取泛型参数的属性，组装成model的getter/setter方法。 通过反射调用形成model的对象数组返回，不需要一个个设置值
	 * 
	 * @param sql 查询的sql
	 * @param obj 查询的sql参数
	 * @return 匹配的数据list
	 */
	public List<T> findBySql(String sql, Object[] params) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		List<T> list = new ArrayList<>();
		connection = DBConnUtils.getConntion();

		T newObj = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (null != params) {
			int len = params.length;
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					pstmt.setObject(index++, params[i]);
				}
			}
		}
		log.info("执行SQL >>> " + sql);
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int colsLen = metaData.getColumnCount();
		while (resultSet.next()) {
			// 通过反射机制创建实例
			newObj = (T) clazz.newInstance();
			for (int i = 0; i < colsLen; i++) {
				String colsName = metaData.getColumnName(i + 1);
				Object colsValue = resultSet.getObject(colsName);
				if (null == colsValue) {
					colsValue = "";
				}
				// 返回一个 Field 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明字段。
				Field field = clazz.getDeclaredField(colsName);
				field.setAccessible(true);// 打开javabean的访问private权限
				// 执行setter方法
				field.set(newObj, colsValue);
			}

			list.add(newObj);
		}
		//  执行完成，关闭数据库链接
		DBConnUtils.closeConn();
		
		return list;
	}
	
	/**
	 * 根据更新、删除数据库数据
	 * @param sql 更新的sql
	 * @param params 参数
	 * @return true-更新、删除成功；false-更新、删除失败
	 */
	public boolean updateBySql(String sql, Object[] params) throws SQLException {
		// 表示当用户执行添加删除和修改的时候所影响数据库的行数
        int result = -1;
		connection = DBConnUtils.getConntion();
        pstmt = connection.prepareStatement(sql);
        int index = 1;  // 占位符下标从1开始
        // 填充sql语句中的占位符
        if (null != params) {
        	int len = params.length;
	        if (len > 0) {
	            for (int i = 0; i < len; i++) {
	                pstmt.setObject(index++, params[i]);
	            }
	        }
        }
		log.info("执行SQL >>> " + sql);
        //  执行更新
        result = pstmt.executeUpdate();
        //  提交事务，持久化到数据库
        connection.commit();
        //  执行完成，关闭数据库链接
		DBConnUtils.closeConn();
        
        return result > 0;
    }
	
	/**
	 * 使用反射机制获取泛型参数的属性，组装成model的getter/setter方法。 通过反射调用形成model的对象数组返回，不需要一个个设置值
	 * 
	 * @param entity 插入的实体对象
	 * @param tableName 插入的表名称
	 * @return true-插入成功；false-插入失败
	 */
	public boolean insertObj(T entity, String tableName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {

		String fieldStr = "";
		String qm = "";
		StringBuffer sb = new StringBuffer();
		if (null == tableName || "".equals(tableName)) 
			tableName = entity.getClass().getSimpleName();
		
		//  insert into tableName(
		sb.append("insert into ").append(tableName).append("( ");
		// 返回一个 Field 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明字段。
		Class<? extends Object> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		int len = fields.length;
		Object[] params = new Object[len];
		String getter = "";
		Method mt = null;
		int k = 0;
		for(int i = 0; i < len; i++) {
			// 得到成员属性名
			fieldStr += (fields[i].getName() + ",");  //  拼接sql语句中 tableName(xxx, xxx) values()
			qm += ("?,");//  拼接sql语句中 values(?,?,?)
			//  执行getter得到对象内的值
			getter = "get" + Utils.toUpperCase(fields[i].getName());
			mt = clazz.getDeclaredMethod(getter);
			params[k++]= mt.invoke(entity);
        }
		// 去掉最后一个逗号
		fieldStr = Utils.removeLastChar(fieldStr, ",");
		qm = Utils.removeLastChar(qm, ",");
		// 拼接字符串得到执行的sql语句
		sb.append(fieldStr).append(" ) values( ").append(qm).append(" )");
		return this.updateBySql(sb.toString(), params);
	}
    
}
