package test.com.book.lxf.impl;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.Set;

import org.junit.Test;

import com.book.lxf.dao.TradeDAO;
import com.book.lxf.domain.Trade;
import com.book.lxf.impl.TradeDAOImpl;

public class TradeDAOImplTest extends TradeDAOImpl {
	
	private TradeDAO tradeDAO = new TradeDAOImpl();

	@Test
	public void testInsertTrade() throws Exception {
		Trade trade = new Trade();
		trade.setUserId(3);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDAO.insert(trade);
	}
	

	@Test
	public void testGetTradesWithUserId() throws Exception {
		Set<Trade> trades  = tradeDAO.getTradesWithUserId(1);
		System.out.println(trades);
	}

}
