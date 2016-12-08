package com.book.lxf.impl;

import com.book.lxf.dao.AccountDAO;
import com.book.lxf.domain.Account;

public class AccountDAOImpl extends BaseDAO<Account> implements AccountDAO {
	
	@Override
	public Account get(Integer accountId) {
		String sql = "select accountId ,balance from account where accountId = ?";
		try {
			return query(sql, accountId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "update account set balance = balance - ? where accountId = ?";
		try {
			update(sql, amount,accountId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
