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

		<div class="errors">
			<form:errors path="profile.*" />
		</div>

		<div class="card">

			<div class="card-header">
				<h5 class="card-header-title"></h5>
				<!--  class="card-text">What's crack-a-lackin?  -->
			</div>

			<!--  <div class="card-body">-->

			<form:form modelAttribute="profile">
				<!--   Tells the kind of object to save data to -->

				<div class="form-group">
					
					<!-- Below is what the TinyMCE docs now suggest -->
					<form:textarea path="about" name="about" rows="10" cols="50"/>
				</div>
				
				<input type="submit" name="submit" value="Save" />
			</form:form>



		</div>
	</div>
	
	<!-- Place the first <script> tag in your HTML's <head> -->
	<script src="https://cdn.tiny.cloud/1/bekjsla78vo2nx9e9447ii0bceql3d0j34y5egcgn5gdj6ju/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
 
    <script>
      tinymce.init({
        selector: 'textarea',
        plugins: "link"
      });
    </script>