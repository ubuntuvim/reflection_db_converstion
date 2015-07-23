package test.com.ubuntuvim.utils;


import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ubuntuvim.utils.DBConnUtils;

public class DBConnUtilsTest {

	private static DBConnUtils dbConnUtil = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConnUtil = new DBConnUtils();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dbConnUtil = null;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetConntion() {
		assertNotNull("对象实例化失败了！！", dbConnUtil);
		assertNotNull("链接数据库失败了！！", DBConnUtils.getConntion());
	}

	@Test
	public void testCloseConn() {
	}

}
