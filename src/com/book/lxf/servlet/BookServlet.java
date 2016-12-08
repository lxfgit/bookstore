package com.book.lxf.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.lxf.domain.Account;
import com.book.lxf.domain.Book;
import com.book.lxf.domain.ShoppingCart;
import com.book.lxf.domain.ShoppingCartItem;
import com.book.lxf.domain.User;
import com.book.lxf.service.AccountService;
import com.book.lxf.service.BookService;
import com.book.lxf.service.UserService;
import com.book.lxf.web.BookStoreWebUtils;
import com.book.lxf.web.CriteriaBook;
import com.book.lxf.web.Page;
import com.google.gson.Gson;


@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private BookService bookService = new BookService();
	private UserService userService = new UserService();
	private AccountService accountService = new AccountService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");

		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 由购物车连接到结账页面
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("WEB-INF/pages/" + page).forward(request, response);
	}

	/**
	 * 清空购物车
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(shoppingCart);
		request.getRequestDispatcher("WEB-INF/pages/empty.jsp").forward(request, response);
	}

	protected void check(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 1、简单验证：验证表单域
		// 的值是否符合基本的规范：是否为空，是否可以转为int类型，是否是一个email，不需要进行查询数据库或者调用任何的业务方法。
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		
		//表单验证通过
		StringBuffer errors = validateFormField(username, accountId);
		
		if (errors.toString().equals("")) {
			errors = validateUser(username, accountId);
			
			//用户名和账户验证通过
			if (errors.toString().equals("")) {
				errors = validateBookStoreNumber(request);
				
				//库存验证通过
				if(errors.toString().equals("")){
					errors = validateBalance(request, accountId);
				}
			}
		}
		if (!errors.toString().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/check.jsp").forward(request, response);
			return;
		}
		
		//验证通过执行具体逻辑操作
		bookService.check(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		response.sendRedirect(request.getContextPath()+"/success.jsp");
	}
	 
	/**
	 * 验证余额是否充足
	 * @param accountId
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public StringBuffer validateBalance(HttpServletRequest request,String accountId) throws NumberFormatException, Exception {
		StringBuffer errors = new StringBuffer("");
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		if(shoppingCart.getTotalMoney()>account.getBalance()){
			errors.append("余额不足！");
		}
		return errors;
	}
	
	/**
	 * 验证库存是否充足
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public StringBuffer validateBookStoreNumber(HttpServletRequest request) throws Exception {
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		StringBuffer errors = new StringBuffer("");

		for(ShoppingCartItem shoppingCartItem:shoppingCart.getItems()){
			int quantity = shoppingCartItem.getQuantity();
			int storeNumber = bookService.getBook(shoppingCartItem.getBook().getId()).getStoreNumber();
			
			if(quantity>storeNumber){
				errors.append("《"+shoppingCartItem.getBook().getTitle()+"》库存不足"+"<br>");
			}
		}
		return errors;
	}

	/**
	 * 验证用户和帐号是否匹配
	 * @param username
	 * @param accountId
	 * @return
	 */
	public StringBuffer validateUser(String username, String accountId) {
		boolean flag = false;
		User user = userService.getUserByUserName(username);
		if (user != null) {
			int accountId2 = user.getAccountId();
			if (accountId.trim().equals("" + accountId2)) {
				flag = true;
			}
		}
		StringBuffer errors2 = new StringBuffer("");
		if (!flag) {
			errors2.append("用户名和帐号不匹配");
		}
		return errors2;
	}

	/**
	 * 进行简单的表单验证
	 * 
	 * @param username
	 * @param accountId
	 * @return
	 */
	public StringBuffer validateFormField(String username, String accountId) {
		StringBuffer errors = new StringBuffer();
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("帐号不能为空" + "<br>");
		}
		if (username == null || username.trim().equals("")) {
			errors.append("用户名不能为空");
		}
		return errors;
	}

	/**
	 * 更新购物车中书本的数量
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 在updateItemQuantity方法中们获取quantity，id。在获取购物车对象，调用service的方法修改。
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");

		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		int id = -1;
		int quantity = -1;

		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {

		}
		if (id > 0 && quantity > 0) {
			bookService.updateItemQuantity(shoppingCart, id, quantity);
		}
		// 传回json数据：bookNumber:xx,totalMoney:xx,
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", shoppingCart.getBookNumber());
		result.put("totalMoney", shoppingCart.getTotalMoney());

		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}

	/**
	 * 获取书本的信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void getBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		int id = -1;

		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		if (id > 0) {
			book = bookService.getBook(id);
		}
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);

	}

	/**
	 * 加入到购物车
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		int id = -1;

		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		if (id > 0) {
			ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
			flag = bookService.addToCart(id, shoppingCart);
		}
		if (flag) {
			getBooks(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
	}

	/**
	 * 删除购物车项目
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;

		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemShoppingCart(shoppingCart, id);
		if (shoppingCart.isEmpty())
			request.getRequestDispatcher("/WEB-INF/pages/empty.jsp");
		else
			request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}

	/**
	 * 获取书本列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}

		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}

		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}

		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = null;
		try {
			page = bookService.getPage(criteriaBook);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("bookpage", page);
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}
}
