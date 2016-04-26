<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@include file="/s_common/taglibs.jsp"%>
<%
System.out.println(1);
response.sendRedirect(request.getContextPath() + "/shop/shop-index.do");
%>

