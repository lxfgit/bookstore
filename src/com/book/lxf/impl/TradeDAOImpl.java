package com.book.lxf.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.book.lxf.dao.TradeDAO;
import com.book.lxf.domain.Trade;

public class TradeDAOImpl extends BaseDAO<Trade> implements TradeDAO{

	@Override
	public void insert(Trade trade) throws Exception {
		String sql = "insert into trade (userid,tradetime) values (?,?)";
		long tradeId = insert(sql, trade.getUserId(),trade.getTradeTime());
		trade.setTradeId((int)tradeId);
		
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) throws Exception {
		String sql = "select tradeId ,userId,tradeTime from trade where userId =? order by tradeTime desc";
		return new LinkedHashSet<Trade>(queryForList(sql, userId));
	}

}
