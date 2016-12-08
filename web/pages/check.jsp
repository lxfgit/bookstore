<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<style>
body {
	margin: 0 auto;
	text-align: center;
}

.Div {
	margin: 0 auto;
	text-align: center;
	width: 600px;
	background-color: #960;
	margin-top: 80px;
	font-size: 18px;
	font-weight: normal;
}

a {
	font-size: 16px;
}
</style>
</head>
<body>
	<form action="bookServlet?method=check" method="post">
	<input type="hidden" name="oldname" value="${param.username}" />
	<input type="hidden" name="oldaccount" value="${param.accountId}" />
		<div class="Div">
			<h3 style="padding-top: 25px;">订单信息</h3>
			<hr>
			<br> 您一共够买了${sessionScope.ShoppingCart.bookNumber}本书 <br> <br>
			应付金额：￥${sessionScope.ShoppingCart.totalMoney} <br> <br>
			<br> <span style="color:red"> <c:if
					test="${requestScope.errors!=null }">
${requestScope.errors}
</c:if>
			</span> <br>
			<br> 信用卡姓名：<input  type="text" name="username" value="" /> <br>
			<br> 信用卡帐号：<input  type="text" name="accountId" value="" /> <br>
			<br> <input type="submit" size="3" value="结账" />&nbsp;&nbsp;&nbsp;
			<a href="bookServlet?method=forwardPage&page=cart.jsp">返回购物车</a><br><br><br>
		</div>
	</form>
</body>
</html>