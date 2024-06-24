<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<br>
<br>

<div class="row">

	<!--  <div class="col-md-8 col-md-offset-2"> -->
	<div class="mx-auto">

		<div class="card">

			<div class="card-header">
				<h5 class="card-header-title">Edit Your Status</h5>
				<!--  <p class="card-text">What's crack-a-lackin?</p> -->
			</div>

			<!--  <div class="card-body">-->
			
				<form:form modelAttribute="statusUpdate">
					<!-- Tells the kind of object to save data to -->

					<form:input type="hidden" path="id" />
					<form:input type="hidden" path="added" />
					
					<div class="errors">
						<form:errors path="text" />
						<!--  
						<form:errors path="id" />
						<form:errors path="added" />
						-->
					</div>
					
					<div class="form-group">
						<!-- Notice how the path below aligns with setText() in statusUpdate class -->
						<form:textarea path="text" name="text" rows="10" cols="50"></form:textarea>
					</div>
					<input type="submit" name="submit" value="Save" />
				</form:form>
		</div>
	</div>
	
	
	<script src='https://cloud.tinymce.com/stable/tinymce.min.js'></script>
  <script>
  tinymce.init({
    selector: 'textarea',
    plugins: "link"
  });
  </script>

<!--  
<h1>TinyMCE Quick Start Guide</h1>
  <form method="post">
    <textarea id="mytextarea">Hello, World!</textarea>
  </form>

  -->
