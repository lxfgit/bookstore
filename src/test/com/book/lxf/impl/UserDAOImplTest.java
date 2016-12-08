package test.com.book.lxf.impl;

import org.junit.Test;

import com.book.lxf.dao.UserDAO;
import com.book.lxf.domain.User;
import com.book.lxf.impl.UserDAOImpl;

public class UserDAOImplTest {
	private UserDAO userDAO = new UserDAOImpl();
	@Test
	public void testGetUser() {
		User user = userDAO.getUser("AAA");
		System.out.println(user);
	}

}
