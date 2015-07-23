package test.com.ubuntuvim.service;

import java.sql.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ubuntuvim.model.User;
import com.ubuntuvim.service.UserService;

public class UserServiceTest {

	private static UserService userService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userService = new UserService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userService = null;
	}

	@Test
	public void testInsertObj() {
		User u = new User();
		u.setBrith(new Date(System.currentTimeMillis()));
		u.setDateil_time(new Date(System.currentTimeMillis()));
		u.setUsername("ubuntuvim");
		System.out.println(" ==== " + userService.insertObj(u, "test_reflection"));;
	}

	@Test
	public void testTestFindHasParams() {
		String sql = "select * from test_reflection where username = ? and id = ?";
		Object[] params = { "username", 1 };
		List<User> users = userService.findBySql(sql, params);
		for (User u : users) {
			System.out.println("id = " + u.getId() 
					+ ", username = " + u.getUsername() 
					+ ", brith = " + u.getBrith() 
					+ ", dateil time = " + u.getDateil_time());
		}
	}

}
