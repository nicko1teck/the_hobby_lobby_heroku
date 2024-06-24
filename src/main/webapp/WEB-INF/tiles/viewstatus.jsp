<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pagi"%>

<c:url var="url" value="/viewstatus" />

<div class="row">

	<div class="mx-auto">

		<pagi:pagination url="${url}" page="${page}" size="3" />

		<c:forEach var="statusUpdate" items="${page.content}">
		
			<c:url var="editLink" value="/editstatus?id=${statusUpdate.id}"/>
			<c:url var="deleteLink" value="/deletestatus?id=${statusUpdate.id}"/>
			
			<div class="card">

				<div class="card-header">
					<!--  <h5 class="card-header-title">Most recent status update, from:</h5>  -->
					Status last updated on:
					<fmt:formatDate pattern="EEEE',' d'th' MMMM y 'at' H:mm:s"
						value="${statusUpdate.added}" />
				</div>

				<div class="card-body">

					<div>${statusUpdate.text}</div>
					
					<div class="edit-links float-right">
					
					<a href="${editLink}">edit </a>|
					<a onclick="return confirm('Really delete this update?')" href="${deleteLink}">delete</a>
					</div>
				
				</div>

			</div>
		</c:forEach>

	</div>
</div>