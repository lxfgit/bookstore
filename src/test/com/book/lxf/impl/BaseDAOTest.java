package test.com.book.lxf.impl;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.book.lxf.domain.Book;
import com.book.lxf.impl.BookDAOImpl;

 
public class BaseDAOTest {

    private BookDAOImpl bookDAOImpl = new BookDAOImpl();

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: insert(String sql,Object... args) 
* 
*/ 
@Test
public void testInsert() throws Exception {
    String sql = "insert into trade (userid,tradetime) values(?,?)";
    long id =  bookDAOImpl.insert(sql, 1, new java.sql.Date(new java.util.Date().getTime()));

    System.out.println(id);
} 

/** 
* 
* Method: update(String sql, Object... args) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    String sql = "update mybooks set salesamount =?  where id= ?";
    bookDAOImpl.update(sql,10,5);
} 

/** 
* 
* Method: query(String sql, Object... args) 
* 
*/ 
@Test
public void testQuery() throws Exception {
    String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark from mybooks where id = ?";
    Book book = (Book) bookDAOImpl.query(sql, 12);
    System.out.println(book);
}

/** 
* 
* Method: queryForList(String sql, Object... args) 
* 
*/ 
@Test
public void testQueryForList() throws Exception {
    String sql = "select id,author,title,price,publishingDate,salesAmount,storeNumber,remark from mybooks where id<? ";

    List<Book> book =bookDAOImpl.queryForList(sql,4);
    System.out.println(book);
} 

/** 
* 
* Method: batch(String sql, Object[]... params) 
* 
*/ 
@Test
public void testBatch() throws Exception {
    String sql = "update mybooks set salesAmount = ?,storeNumber = ? where id = ?";
    bookDAOImpl.batch(sql,new Object[]{1,1,1},new Object[]{2,2,2},new Object[]{3,3,3});
}

/** 
* 
* Method: getSingleVal(String sql, Object... args) 
* 
*/ 
@Test
public void testGetSingleVal() throws Exception {
    String sql = "select count(id) from mybooks";
    Object count = bookDAOImpl.getSingleVal(sql);
    System.out.println(count);
} 


} 
