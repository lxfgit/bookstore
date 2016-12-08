package com.book.lxf.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.book.lxf.domain.ShoppingCart;

/**
 * 获取购物车对象：从session中获取，若session中没有该对象 则创建一个新的购物车对象，放入到session中。 如有，则直接返回
 * 
 * @author LXF
 *
 */
public class BookStoreWebUtils {
	public static ShoppingCart getShoppingCart(HttpServletRequest request) {
		HttpSession session = request.getSession();

		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("ShoppingCart");
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			session.setAttribute("ShoppingCart", shoppingCart);
		}

		return shoppingCart;
	}
}
