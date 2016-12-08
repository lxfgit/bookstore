package com.book.lxf.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.lxf.domain.User;
import com.book.lxf.service.UserService;


@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		//调用UserServlet的getUser（username）的方法获取User：要求trades是被封装好的，而且每一个Trade的对象items也是被封装好的。
		User user = null;
		
		try {
			user = userService.getUserWithTrades(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//把user对象放到request中
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
			return;
		}

		request.setAttribute("user", user);
		//装法页面到/WEB-INF/pages/trades.jsp
		request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request, response);
	}

}
