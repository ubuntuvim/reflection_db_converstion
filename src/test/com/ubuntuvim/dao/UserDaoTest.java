package test.com.ubuntuvim.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

import com.ubuntuvim.dao.UserDao;
import com.ubuntuvim.model.User;

public class UserDaoTest {
	
	private UserDao userDao = new UserDao();
	
	@Test
	public void testFindAll() {
		try {
			String sql = "select * from user";
			List<User> users = userDao.findBySql(sql, null);
			for (User u : users) {
				System.out.println(u);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTestFindHasParams() {
		try {
			String sql = "select * from user where username = ? and id = ?";
			Object[] params = { "ubuntuvim", 30 };
			List<User> users = userDao.findBySql(sql, params);
			for (User u : users) {
				System.out.println(u);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
