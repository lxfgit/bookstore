package test.com.book.lxf.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.book.lxf.dao.TradeItemDAO;
import com.book.lxf.domain.Trade;
import com.book.lxf.domain.TradeItem;
import com.book.lxf.impl.TradeItemDAOImpl;

public class TradeItemDAOTest {

	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	@Test
	public void testBatchSave() throws Exception {
		Collection<TradeItem> items = new ArrayList<>();

		items.add(new TradeItem(null,2,20,18));
		items.add(new TradeItem(null,8,50,18));
		items.add(new TradeItem(null,4,30,18));
		tradeItemDAO.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTradeId() throws Exception {
		Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(18);
		System.out.println(items);
	}

}
