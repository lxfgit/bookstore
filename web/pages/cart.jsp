<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/commons/queryCondition.jsp"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>图书信息</title>
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".del").click(function() {
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要把" + title + "从购物车中删除吗？");
			if (flag) {
				return true;
			}
			return false;
		});

		$(".clear").click(function() {
			var flag = confirm("确定要清空购物车吗？");
			if (!flag) {
				return false;
			}
		});

		//获取所有text。添加onchange函数，confirm信息。
		$(":text").change(function() {
			var quantityVal = $.trim(this.value);

			var flag = false;
			var reg = /^\d+$/g;
			if (reg.test(quantityVal)) {
				var quantity = parseInt(quantityVal);
				if (quantity >= 0) {
					flag = true;
				}
			} 

			if (!flag) {
				alert("输入的数量不合法！");
				$(this).val($(this).attr("class"));
				return;
			}

			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());

			if (quantity == 0) {
				var flag2 = confirm("确定要把" + title + "从购物车中删除吗？");
				if (flag2) {
					var href = $tr.find("td:last").find("a").attr("href");
					var serializeVal = $(":hidden").serialize();
					href = href + "&" + serializeVal;
					window.location.href = href;
					return;
				}
				$(this).val($(this).attr("class"));
				return;
			}

			var flag = confirm("确定要修改" + title + "的数量吗？");

			if (!flag) {
				$(this).val($(this).attr("class"));
				return;
			}

			//请求地址为：bookServlet
			var url = "bookServlet";
			//请求参数：menthod：updateItemQuantity，id:xxx，quantity：val,time:new Date()
			var idVal = $.trim(this.name);
			var args = {
				"method" : "updateItemQuantity",
				"id" : idVal,
				"quantity" : quantityVal,
				"time" : new Date()
			};

			//更新当前页面的bookNumber和totalMoney
			$.post(url, args, function(data) {
				var bookNumber = data.bookNumber;
				var totalMoney = data.totalMoney;

				//alert(bookNumber);
				//alert(totalMoney);
				$("#totalMoney").text("总金额 ：￥" + totalMoney);
				$("#bookNumber").text("您的购物车共有" + bookNumber + "本书");

			}, "JSON")
		});
	})
</script>
<style type="text/css">
body {
	background: silver;
	text-align: center;
}

.Div {
	margin: 0 auto;
	width: 800px;
	background-color: #960;
	margin-top: 20px;
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
th{
	color:yellow;
	font-size:18px;
} 
td{
	text-align:center;
	font-size:16px;
}


input {
	background-color: #950;
	border: none;
	text-align: center;
}
</style>
</head>
<body>
	<h1 align="center" style="margin-top: 40px">购物车信息</h1>
	<hr />
	<div class="Div">
		<h3 style= "padding-top: 25px;" id="bookNumber">您的购物车中共有${sessionScope.ShoppingCart.bookNumber}本书</h3>
			<hr color="black" />
		<table width="80%" align="center" cellspacing="10">
			<tr>
				<th width="40%">书名</th>
				<th>单价</th>
				<th width="25%">数量</th>
				<th>操作</th>
			</tr>

			<c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
				<tr>
					<td>《${item.book.title}》</td>
					<td>${item.book.price}</td>
					<td><input class="${item.quantity}" type="text" size="2"
						name="${item.book.id }" value="${item.quantity}" /></td>
					<td><a
						href="bookServlet?method=remove&pageNo=${param.pageNo}&id=${item.book.id}"
						class="del">删除</a></td>
				</tr>
			</c:forEach>
			<tr></tr>
			<tr>
				<td colspan="4" id="totalMoney">总金额：￥${sessionScope.ShoppingCart.totalMoney}</td>
			</tr>

			<tr>
				<td colspan="4"><a
					href="bookServlet?method=getBooks&pageNo=${param.pageNo}">继续购物</a>
					<a id="clear"
					href="bookServlet?method=clearCart&pageNo=${param.pageNo}&id=${item.book.id}"
					class="clear">清空购物车</a> <a href="bookServlet?method=forwardPage&page=check.jsp">结账</a><br><br>
			</tr>
		</table>
	</div>

</body>
</html>