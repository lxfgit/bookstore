package test.com.book.lxf.impl;

import org.junit.Test;

import com.book.lxf.dao.AccountDAO;
import com.book.lxf.domain.Account;
import com.book.lxf.impl.AccountDAOImpl;

public class AccountDAOImplTest {	

	AccountDAO accountDAO = new AccountDAOImpl();
	
	@Test
	public void testGet() throws Exception {
		Account account =  accountDAO.get(1);
		System.out.println(account.getBalance());
	}

	@Test
	public void testUpdateBalance() {
		accountDAO.updateBalance(1, 200);
	}

}
