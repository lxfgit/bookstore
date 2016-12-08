package test.com.book.lxf.impl; 

import com.book.lxf.dao.BookDAO;
import com.book.lxf.db.JDBCUtils;
import com.book.lxf.domain.Book;
import com.book.lxf.domain.ShoppingCartItem;
import com.book.lxf.impl.BookDAOImpl;
import com.book.lxf.web.ConnectionContext;
import com.book.lxf.web.CriteriaBook;
import com.book.lxf.web.Page;
import java.sql.Connection;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After; 

/** 
* BookDAOImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 20, 2015</pre> 
* @version 1.0 
*/ 
public class BookDAOImplTest {

    private BookDAO bookDAO = new BookDAOImpl();

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getBook(int id) 
* 
*/ 
@Test
public void testGetBook() throws Exception {
	Connection connection = JDBCUtils.getConnection();
	ConnectionContext.getInstance().bind(connection);
    Book book = bookDAO.getBook(5);
    System.out.println(book);
}

/** 
* 
* Method: getPage(CriteriaBook cb) 
* 
*/ 
@Test
public void testGetPage() throws Exception {
    CriteriaBook cb = new CriteriaBook(50,60,90);
    Page<Book> page = bookDAO.getPage(cb);
    System.out.println("pageNo : " + page.getPageNo());
    System.out.println("totalPageNumber : " + page.getTotalPageNumber());
    System.out.println("list : " + page.getList());
    System.out.println("prePage : "+ page.getPrePage());
    System.out.println("nextPage : "+page.getNextPage());
} 

/** 
* 
* Method: getTotalBookNumber(CriteriaBook cb) 
* 
*/ 
@Test
public void testGetTotalBookNumber() throws Exception {

} 

/** 
* 
* Method: getPageList(CriteriaBook cb, int pageSize) 
* 
*/ 
@Test
public void testGetPageList() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getStoreNumber(Integer id) 
* 
*/ 
@Test
public void testGetStoreNumber() throws Exception {
    int storeNumber = bookDAO.getStoreNumber(5);
    System.out.println(storeNumber);
} 

/** 
* 
* Method: batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) 
* 
*/ 
@Test
public void testBatchUpdateStoreNumberAndSalesAmount() throws Exception {
	Collection<ShoppingCartItem> items = new ArrayList<>();
	Book book = bookDAO.getBook(1);
	ShoppingCartItem shoppingCartItem = new ShoppingCartItem(book);
	shoppingCartItem.setQuantity(11);
	items.add(shoppingCartItem);
	
	book = bookDAO.getBook(2);
	shoppingCartItem = new ShoppingCartItem(book);
	shoppingCartItem.setQuantity(12);
	items.add(shoppingCartItem);
	
	book = bookDAO.getBook(3);
	shoppingCartItem = new ShoppingCartItem(book);
	shoppingCartItem.setQuantity(10);
	items.add(shoppingCartItem);
	
	book = bookDAO.getBook(4);
	shoppingCartItem = new ShoppingCartItem(book);
	shoppingCartItem.setQuantity(14);
	items.add(shoppingCartItem);
	
	bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
}


} 
