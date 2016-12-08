package com.book.lxf.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.book.lxf.dao.BookDAO;
import com.book.lxf.domain.Book;
import com.book.lxf.domain.ShoppingCartItem;
import com.book.lxf.web.CriteriaBook;
import com.book.lxf.web.Page;

/**
 * Created by LXF on 2015/11/19.
 */
public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

	@Override
	public Book getBook(int id) throws Exception {
		String sql = "select id,author ,title ,price,publishingDate,salesAmount,storeNumber,remark from mybooks where id =?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) throws Exception {
		Page page = new Page<>(cb.getPageNo());

		page.setTotalItemNumber(getTotalBookNumber(cb));
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 5));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) throws Exception {
		String sql = "select count(id) from mybooks where price >=? and" + " price <=?";

		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrive());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) throws Exception {
		String sql = "select id,author,title ,price,publishingDate,salesAmount,storeNumber,remark from mybooks where price >=? and"
				+ " price <=? limit ?,?";

		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrive(), (cb.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) throws Exception {
		String sql = "select storeNumber from mybooks where id =?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items){
		String sql = "update mybooks set salesAmount = salesAmount+ ?,storeNumber = storeNumber-? where id = ?";
		Object [][] params = null;
		params = new Object[items.size()][3];
		List<ShoppingCartItem> shoppingCartItems =new ArrayList<>(items);
		for(int i = 0;i<items.size();i++){
			params[i][0] = shoppingCartItems.get(i).getQuantity();
			params[i][1] = shoppingCartItems.get(i).getQuantity();
			params[i][2] = shoppingCartItems.get(i).getBook().getId();
		}
		try {
			batch(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
