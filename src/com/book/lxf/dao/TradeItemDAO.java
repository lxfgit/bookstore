package com.book.lxf.dao;

import com.book.lxf.domain.TradeItem;

import java.util.Collection;
import java.util.Set;

public interface TradeItemDAO {

	/**
	 * 批量保存 TradeItem 对象
	 * @param items
	 * @throws Exception 
	 */
	public abstract void batchSave(Collection<TradeItem> items) throws Exception;
	
	/**
	 * 根据 tradeId 获取和其关联的 TradeItem 的集合
	 * @param tradeId
	 * @return
	 * @throws Exception 
	 */
	public abstract Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) throws Exception;

}

