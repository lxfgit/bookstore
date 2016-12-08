<%--
  Created by IntelliJ IDEA.
  User: LXF
  Date: 2015/11/17
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding ="UTF-8"%>
<html>
  <head>
    <title></title>
  </head>
  <body>
<%
  response.sendRedirect(request.getContextPath()+"/bookServlet?method=getBooks");
%>
  </body>
</html>
