<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	background: sliver;
	text-align: center;
}

.Div {
	margin: 0 auto;
	width: 600px;
	background-color: #960;
	margin-top: 20px;
	font-size: 24px;
	font-weight: normal;
	line-height: em;
	text-align: center;
}
</style>
</head>
<body>
	<div class="Div">
		<form action="userServlet" method="post">
			<div class="Div">
				User:${user.username }<br> <br>
				<c:forEach items="${user.trades }" var="trade">
 TradeTime:${trade.tradeTime }<hr width="50%">
					<br>
					<br>
					<table border="0" width="70%"
						style="text-align: center; padding: 0 auto;">
						<tr>
							<th>书名</th>
							<th>单价</th>
							<th>数量</th>
						</tr>
						<c:forEach items="${trade.items }" var="item">
							<tr>
								<td>${item.book.title }</td>
								<td>${item.book.price }</td>
								<td>${item.quantity }</td>
							</tr>
						</c:forEach>
					</table>
					<br>
					<br>
					<hr>
					<br>
				</c:forEach>
			</div>
		</form>
	</div>
</body>
</html>