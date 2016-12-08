<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ include file = "/commons/queryCondition.jsp" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>图书信息</title>
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
a:link,
a:visited {
	color:blue;
	text-decoration:none;
}
a:hover,
a:active {
	color:#000000;
	text-decoration:none;
} 

</style>
</head>
<body>
	<h1 align="center" style="margin-top: 100px">图书信息</h1>
	<hr /> 
	<div class="Div">
		<br> Title:${book.title }
		<hr width=600px; />
		Author:${book.author }
		<hr width=600px; />
		Price:${book.price }
		<hr width=600px; />
		PublishDate:${book.publishingDate }
		<hr width=600px; />
		Remark:${book.remark}
		<hr width=600px; />
	</div>
	<a href="bookServlet?method=getBooks&pageNo=${param.pageNo}"><font size="4">继续购物</font></a>
</body>
</html>