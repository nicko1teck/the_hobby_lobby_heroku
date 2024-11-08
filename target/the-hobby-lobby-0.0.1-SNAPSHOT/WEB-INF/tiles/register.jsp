<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<br>
<br>

<c:url var="loginUrl" value="/login" />


<div class="i-am-centered">
<div class="row">

	<!--  <div class="col-md-8 col-md-offset-2"> -->
	<div class="mx-auto">

		<div class="login-error">
			<form:errors path="user.*" />
		</div>
		
		<div class="card">

			<div class="card-header">
				<h5 class="card-header-title">Create an Account</h5>

			</div>

			<div class="card-body">

				<form:form method="post" modelAttribute="user" class="login-form">

					<div class="input-group">
						<form:input type="text" path="firstname"
							placeholder="First Name" class="form-control" />
							
							<span class="input-group-btn" style="width:20px"></span>

						<form:input type="text" path="surname"
							placeholder="Last Name" class="form-control" />
					</div>
					
				
					<div class="input-group">
						<form:input type="text" path="email"
							placeholder="enter your email address" class="form-control" />
					</div>

					<div class="input-group">
						<form:input type="password" path="plainPassword" placeholder="password"
							class="form-control" />
					</div>

					<div class="input-group">
						<form:input type="password" path="repeatPassword"
							placeholder="repeat password" class="form-control" />
					</div>

					<div class="input-group">
						<button type="submit" class="btn-primary pull-right">
							Register</button>
					</div>

				</form:form>
			</div>

		</div>

	</div>

</div>

</div>
