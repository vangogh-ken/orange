<%@page language="java" pageEncoding="UTF-8" %>

<%@ page import="com.van.halley.bpm.support.ProcessDefinitionCache" %>
<%@ page import="com.van.halley.bpm.support.TaskQueryCache" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.activiti.engine.RepositoryService" %>
<%@ page import="org.activiti.engine.HistoryService" %>
<%@ page import="com.van.service.BpmConfigNodeService" %>
<%@ page import="com.van.service.BpmConfigOperationService" %>
<%@ page import="com.van.service.BasisSubstanceService" %>
<%@ page import="com.van.service.UserService" %>
<%@ page import="org.apache.commons.lang3.StringUtils,org.apache.commons.lang3.ObjectUtils" %>
<%@ page import="com.van.halley.util.file.FileUtil" %>
<%@ page import="com.van.halley.core.util.UserUtil" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%pageContext.setAttribute("ctx",  request.getContextPath());%>
<%pageContext.setAttribute("httpCtx",  "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());%>

<%pageContext.setAttribute("locale",  request.getLocale());%>
<%pageContext.setAttribute("resources",  request.getSession().getAttribute("resources"));%>
<%pageContext.setAttribute("currentResource",  request.getSession().getAttribute("currentResource"));%>
<%pageContext.setAttribute("resKeys",  request.getSession().getAttribute("resKeys"));%>
<%pageContext.setAttribute("currentUser",  request.getSession().getAttribute("userSession"));%>

<%pageContext.setAttribute("unreadMails",  request.getSession().getAttribute("unreadMails"));%>
<%pageContext.setAttribute("unreadMsgs",  request.getSession().getAttribute("unreadMsgs"));%>
<%pageContext.setAttribute("claimTasks",  request.getSession().getAttribute("claimTasks"));%>
<%pageContext.setAttribute("handleTasks",  request.getSession().getAttribute("handleTasks"));%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="van" uri="/van-tag" %>
