<%--
  Created by IntelliJ IDEA.
  User: LXF
  Date: 2015/11/20
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/queryCondition.jsp"%>

<html>
<head>
<title>购物</title>
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#pageNo")
				.change(
						function() {
							var val = $(this).val();
							val = $.trim(val);
							var reg = /^\d+$/g;
							var pageNo = 0;
							var flag = false;
							if (reg.test(val)) {
								pageNo = parseInt(val);
								if (pageNo >= 1
										&& pageNo <= parseInt("${bookpage.totalPageNumber}")) {
									flag = true;
								}
							}

							if (!flag) {
								alert("输入的不是合法的页码");
								$(this).val("");
								return;
							}

							var href = "bookServlet?method=getBooks&pageNo="
									+ pageNo + "&" + $(":hidden").serialize();
							window.location.href = href;
						});
	});
</script>
<style type="text/css">
body {
	background: silver;
	text-align: center;
}

.fDiv {
	margin: 0 auto;
	text-align:center;
	width: 700px;
	margin-top: 200px;
	background-color: #960;
	font-size:20px;
}
a:link,
a:visited {
	color:blue;
	text-decoration:none;
	font-size:20px;
}
a:hover,
a:active {
	color:#000000;
text-decoration:none;
	font-size:20px;

} 

th {
	text-align: center;
	color: yellow;
	font-size:22px;
}

td {
	text-align: center;
}
</style>
</head>
<%@ include file="/commons/queryCondition.jsp"%>
<body>
	<div class="fDiv">
		<form action="bookServlet?method=getBooks" method="post" style="padding-top:15px;">
			Price:<input type="text" size="2" name="minPrice" />——<input
				type="text" size="2" name="maxPrice" /> <input type="submit"
				value="submit" />
		</form>
		<br /> 
		<table style="margin: 0 auto;width:90%">
			<tr>
				<th width="30%">书名</th>
				<th>作者</th>
				<th width = "25%">价格</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${bookpage.list}" var="book">
				<tr>
					<td><a
						href="bookServlet?method=getBook&pageNo=${bookpage.pageNo}&id=${book.id}">${book.title}</a></td>
					<td>${book.author}</td>
					<td>${book.price}</td>
					<td><a
						href="bookServlet?method=addToCart&pageNo=${bookpage.pageNo}&id=${book.id}&title=${book.title}">加入购物车</a></td>
				</tr>
			</c:forEach>

		</table>
		<br /> <br /> 共${bookpage.totalPageNumber}页 &nbsp;&nbsp;
		当前第${bookpage.pageNo} &nbsp;&nbsp;
		<c:if test="${bookpage.hasPre}">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
    &nbsp;&nbsp;
    <a href="bookServlet?method=getBooks&pageNo=${bookpage.prePage}">上一页</a>
		</c:if>

		<c:if test="${bookpage.hasNext}">
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.nextPage}">下一页</a>
     &nbsp;&nbsp;
      <a
				href="bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber}">末页</a>
		</c:if>

		&nbsp;&nbsp; 转到<input type="text" size="2" id="pageNo" />页 <br>
		<br> <br>


		<c:if test="${!empty sessionScope.ShoppingCart.books}">
			您的购物车中有${sessionScope.ShoppingCart.bookNumber}本书，
			<a href="bookServlet?method=forwardPage&page=cart.jsp&pageNo=${bookpage.pageNo}">查看购物车</a>
		</c:if>
		<br> <br>
		<c:if test="${param.title != null}">
			您已经将  《${param.title}》  放入购物车中
			<br>
			<br>
		</c:if>
	</div>

</body>
</html>
