<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<br>
<br>

<c:url var="loginUrl" value="/login" />
<c:url var="registerUrl" value = "/register"/>

<div class="row register-prompt">

	<!--  <div class="col-md-8 col-md-offset-2"> -->
	<div class="mx-auto">
	
	Please login to continue, or click <a href="${registerUrl}"> here </a>to create an account.
	
	</div>
	
</div>

<div class="i-am-centered">

	<div class="row">

		
	

		<c:if test="${param.error != null}">
			<div class="login-error">Incorrect username or password.</div>
		</c:if>

		
	
		

			<div class="card-header">
				<h5 class="card-header-title">User Login</h5>
			</div>

			<div class="card-body">

				<form method="post" action="${loginUrl}" class="login-form">
				
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"  />
					
					<div class="input-group"> 
					<input type="text" name="username" placeholder="enter your username" class="form-control" /> 
					</div>
					
					<div class="input-group">
					<input type="password" name="password" placeholder="password" class="form-control" /> 
					</div>
					
					<div class="input-group">
					<button type="submit" class="btn-primary pull-right"> submit </button>
					</div>
				</form>
			</div>

		

	

	</div>
</div>
