package com.book.lxf.dao;

import com.book.lxf.domain.Trade;

import java.util.Set;

public interface TradeDAO {

	/**
	 * 向数据表中插入 Trade 对象
	 * @param trade
	 * @throws Exception 
	 */
	public abstract void insert(Trade trade) throws Exception;
	/**
	 * 根据 userId 获取和其关联的 Trade 的集合
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId) throws Exception;

}