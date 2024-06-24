<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:url var="ouboundDestination" value="app/message/send/${chatWithUserID}" />
<c:url var="inboundDestination" value="user/queue/${chatWithUserID}" />

<!--  https://stackoverflow.com/questions/20740956/spring-4-websocket-app   -->

<!--  DEBUG
	We are: <c:out value="${thisUserName}" />, with ID: <c:out value="${thisUserID}" /><br>
	Chatting with: <c:out value="${chatWithUserName }" />, with ID: <c:out value="${chatWithUserID}" />
-->

<div class="row">

	<div class="col-md-12">

		<div class="card">

			<div class="card-header">
				<h5 class="card-header-title">
					Chatting with
					<c:out value="${chatWithUserName}" />
				</h5>
			</div>

			<div class="card-body">
				<div id="chat-message-view">

					
					<div id="chat-message-previous">
						<a id="chat-older-messages" href="#">View older messages</a>
					</div>	

					<div id="chat-message-record"></div>

					<div id="chat-message-send" class="input-group input-group-lg">

						<textarea class="form-control" id="chat-message-text"
							placeholder="Enter message"></textarea>

						<span class="input-group-btn">
							<button id="chat-send-button" class="btn btn-primary"
								type="button">Send</button>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>