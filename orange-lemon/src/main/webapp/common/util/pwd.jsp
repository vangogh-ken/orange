<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.van.util.StringUtil"%>
<%
	if (request.getParameter("password") != null) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
		pageContext.setAttribute("encodedPassword", StringUtil.encodeMd5(request.getParameter("password")));
	}
%>
<html>
  <head>
    <meta charset="utf-8">
	<title>pwd</title>
    <style>
tbody tr:nth-child(odd) td,
tbody tr:nth-child(odd) th {
  background-color: #f9f9f9;
}
    </style>
  </head>
  <body>
	<form action="pwd.jsp" method="post">
	  <label for="password">password:</label>
	  <input id="password" type="text" name="password" value="${param.password}">
	  <br>
	  <label>encodedPassword: ${encodedPassword}</label><br>
	  <input type="submit" value="编码">
	</form>
	<hr>
	admin : 21232f297a57a5a743894a0e4a801fc3
	<br>
	user : ee11cbb19052e40b07aac0ca060c23ee
	<br>
  </body>
</html>
