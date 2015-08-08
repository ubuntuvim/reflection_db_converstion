/**
 * 
 */
package com.ubuntuvim.dao;

import com.ubuntuvim.core.CoreDao;
import com.ubuntuvim.model.User;

/**
 * 继承CoreDao，并把实体类 User 作为泛型的类型传入到 CoreDao
 * @author ubuntuvim
 * @email 1527254027@qq.com
 * @datatime 2015-7-23 下午11:25:02
 */
public class UserDao extends CoreDao<User> {
	
}
