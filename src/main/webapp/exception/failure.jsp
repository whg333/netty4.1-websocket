<%@page import="com.whg.util.exception.StackTraceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
	<title>whg game</title>
	<META HTTP-EQUIV="cache-control" CONTENT="no-cache,must-revalidate">
	<META HTTP-EQUIV="Expires" CONTENT="-1">
	<META HTTP-EQUIV="pragma" CONTENT="no-nache">

</head>
  <body>
   <div align="center" class="css">
		<c:choose>
			<c:when test="${exception!=null}">
				${exception.message}
			</c:when>
			<c:otherwise>
				系统出错！请稍后重试.....
			</c:otherwise>
		</c:choose>
	</div>
	
	<%
		Throwable t = (Throwable)request.getAttribute("exception");
		System.err.println(StackTraceUtil.getStackTrace(t));
		out.print("<p><font color='red'>"+StackTraceUtil.getStackTrace(t)+"</font></p>");
	%>
	
	</body>
</html>

