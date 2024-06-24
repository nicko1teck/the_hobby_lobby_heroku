<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<sec:authorize access="isAuthenticated()">

<c:url var="websocketEndpoint" value="/chat" scope="request" />
<c:url var="notificationsUrl" value="/" />
<c:url var="notificationsQueue" value="/user/queue/newmessages" />


	<script>
		function alertUser(from, text) {
			if (!("Notification" in window)) {
				alert("This browser does not support desktop notification");
				return;
			} 
			else if (Notification.permission === "denied") {
				 return;
			} 
			else if (Notification.requestPermission !== "granted") {
				Notification.requestPermission();
			}
			if (Notification.permission === "granted") {
				var notification = new Notification(from, {body:text});
				notification.onclick = function() {
					window.location.href= "${notificationsUrl}";
					}
				}
		}

		var connectionManager = new ConnectionManager("${websocketEndpoint}");

		connectionManager.addSubscription("${notificationsQueue}", function(
				messageJson) {
			var message = JSON.parse(messageJson.body);
			alertUser(message.from, message.text);
			//alert(message.text);
		});
	</script>

</sec:authorize>

</head>

<body>

</body>

</html>