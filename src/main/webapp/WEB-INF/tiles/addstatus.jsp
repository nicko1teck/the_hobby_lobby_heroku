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
				<h5 class="card-header-title">Update Your Status</h5>
				<!--  <p class="card-text">What's crack-a-lackin?</p> -->
			</div>

			<!--  <div class="card-body">-->
				<form:form modelAttribute="statusUpdate">
					<!-- Tells the kind of object to save data to -->

					<!--<form:input type="hidden" path="id" />-->

					<div class="errors">
						<form:errors path="text" />
					</div>
					
					<div class="form-group">
						<!-- Notice how the path below aligns with setText() in statusUpdate class -->
						<form:textarea path="text" name="text" rows="10" cols="50"></form:textarea>
					</div>
					<input type="submit" name="submit" value="Add Status" />
				</form:form>
				<!--  <a href="#" class="btn btn-primary">Go somewhere</a>  -->
			<!--  </div>-->

			<!-- I added a second card body to the original card for troubleshooting reasons, but now I think I know why
			my attempt to simply add a second card, header and all, wasn't working, so I should come back and try that.  It was a property
			reference issue with added and added.text, I think.  should have just been 'added' and 'text'.  They are both properties
			of the same object -->

			<div class="card">
				<div class="card-header">
					<!--  <h5 class="card-header-title">Most recent status update, from:</h5>  -->
					Status last updated on:
					<fmt:formatDate pattern="EEEE',' d'th' MMMM y 'at' H:mm:s"
						value="${latestStatusUpdate.added}" />
				</div>
				<div class="card-body">
					${latestStatusUpdate.text}
					<!--  <a href="#" class="btn btn-primary">Go somewhere</a>  -->
				</div>
			</div>

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
