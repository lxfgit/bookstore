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
	text-align: center;
	width: 600px;
	background-color: #960;
	margin-top: 50px;
	font-size: 24px;
	font-weight: normal;
	line-height: em;
}
</style>

</head>
<body>
	<div class="Div">
		<form action="userServlet" method="post">
			用户名：<input id="user" type="text" name="username" /><br>
			<br> <input type="submit" value="查询" name="submit"/>
		</form>
	</div>
</body>
</html>