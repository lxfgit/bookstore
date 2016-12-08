package com.book.lxf.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.lxf.db.JDBCUtils;
import com.book.lxf.web.ConnectionContext;

/**
 * Servlet Filter implementation class TransactionFilter
 */
@WebFilter("/*")
public class TransactionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TransactionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection connection = null;
		try {
//			1、获取连接
			connection  = JDBCUtils.getConnection();
//			2、开启事务
			connection.setAutoCommit(false);
//			3、利用ThreadLocal把链接和当前线程绑定
			ConnectionContext.getInstance().bind(connection);
//			4、把请求扎un给目标Servlet
			chain.doFilter(request, response);
//			5、提交事务
			connection.commit();
		} catch ( Exception e) {
//			6、回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			HttpServletResponse res = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			res.sendRedirect(req.getContextPath()+"/error-1.jsp");
		}finally{
//			7、解除绑定
			ConnectionContext.getInstance().remove();
//			8、关闭连接
			try {
				JDBCUtils.releaseConnection(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
