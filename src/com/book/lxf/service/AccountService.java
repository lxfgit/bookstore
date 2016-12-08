package com.book.lxf.service;

import com.book.lxf.dao.AccountDAO;
import com.book.lxf.domain.Account;
import com.book.lxf.impl.AccountDAOImpl;

public class AccountService {

	private AccountDAO accountDAO = new AccountDAOImpl();

	public Account getAccount(int accountId) throws Exception{
		return accountDAO.get(accountId);
	}
}
