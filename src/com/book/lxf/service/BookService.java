package com.book.lxf.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.book.lxf.dao.AccountDAO;
import com.book.lxf.dao.BookDAO;
import com.book.lxf.dao.TradeDAO;
import com.book.lxf.dao.TradeItemDAO;
import com.book.lxf.dao.UserDAO;
import com.book.lxf.domain.Book;
import com.book.lxf.domain.ShoppingCart;
import com.book.lxf.domain.ShoppingCartItem;
import com.book.lxf.domain.Trade;
import com.book.lxf.domain.TradeItem;
import com.book.lxf.impl.AccountDAOImpl;
import com.book.lxf.impl.BookDAOImpl;
import com.book.lxf.impl.TradeDAOImpl;
import com.book.lxf.impl.TradeItemDAOImpl;
import com.book.lxf.impl.UserDAOImpl;
import com.book.lxf.web.CriteriaBook;
import com.book.lxf.web.Page;

public class BookService {
	private BookDAO bookDAO = new BookDAOImpl();
	private AccountDAO accountDAO = new AccountDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	public Page<Book> getPage(CriteriaBook criteriaBook) throws Exception {
		return bookDAO.getPage(criteriaBook);
	}

	public Book getBook(int id) throws Exception {
		return bookDAO.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart shoppingCart) throws Exception {
		Book book = bookDAO.getBook(id);
		if (book != null) {
			shoppingCart.addBook(book);
			return true;
		}
		return false;
	}

	public void removeItemShoppingCart(ShoppingCart shoppingCart, int id) {
		shoppingCart.removeItem(id);
	}

	public void clearShoppingCart(ShoppingCart shoppingCart) {
		shoppingCart.clear();
	}

	public void updateItemQuantity(ShoppingCart shoppingCart, int accountId, int quantity) {
		shoppingCart.updateItemQuantity(accountId, quantity);
	}
	//业务方法
	public void check(ShoppingCart shoppingCart, String username, String accountId) throws Exception {
		// 更新mybooks数据表相关记录的salesamount和storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		// 更新account数据表的balance
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		// 想trade数据插入一条记录
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		// 想tradeitem数据表插入n条记录
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci:shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		// 清空购物车
		shoppingCart.clear();
	}
}
