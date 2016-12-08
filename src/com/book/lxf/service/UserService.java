package com.book.lxf.service;

import java.util.Set;

import com.book.lxf.dao.BookDAO;
import com.book.lxf.dao.TradeDAO;
import com.book.lxf.dao.TradeItemDAO;
import com.book.lxf.dao.UserDAO;
import com.book.lxf.domain.Trade;
import com.book.lxf.domain.TradeItem;
import com.book.lxf.domain.User;
import com.book.lxf.impl.BookDAOImpl;
import com.book.lxf.impl.TradeDAOImpl;
import com.book.lxf.impl.TradeItemDAOImpl;
import com.book.lxf.impl.UserDAOImpl;

public class UserService {

	private UserDAO userDAO = new UserDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();
	
	public User getUserByUserName(String username) {
		return userDAO.getUser(username);
	}

	public User getUserWithTrades(String username) throws Exception {

		User user = userDAO.getUser(username);

		if (user == null) {
			return null;
		}

		int userId = user.getUserId();

		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		
		if (trades != null) {
			for (Trade trade : trades) {
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				if(items!=null){
					for(TradeItem item:items){
						item.setBook(bookDAO.getBook(item.getBookId()));
					}
					trade.setItems(items);
				}
				
			}
		}
		user.setTrades(trades);
		return user;
	}
}
