package com.book.lxf.impl;

import com.book.lxf.dao.UserDAO;
import com.book.lxf.domain.User;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		String sql = "select userId,username,accountId from userinfo where username =?";
		try {
			return query(sql, username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
