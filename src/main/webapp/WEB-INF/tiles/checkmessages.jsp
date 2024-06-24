<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pagi"%>

<c:url var="url" value="/messages" />

<c:forEach var="message" items="${messageList.content}">

	<c:url var="messageUrl" value="/markread?f=${message.fromUserID}&m=${message.id}" />
	
	<div class="message-received">
	
		${message.id}
		${message.sent } <a href="${messageUrl}">${message.from }</a>
	
	</div>
</c:forEach>

<pagi:pagination url="${url}" page="${messageList}" size="3" />