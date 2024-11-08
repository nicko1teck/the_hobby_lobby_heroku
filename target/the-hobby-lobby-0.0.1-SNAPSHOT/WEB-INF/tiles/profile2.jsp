<!doctype html>
<html lang="en">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<!--<c:url var="img" value="/profilephoto/${userId}" />-->
<c:url var="profilePhoto" value="/profilephoto/${userId}" />
<c:url var="editProfileAbout" value="/edit-profile-about" />
<c:url var="saveInterest" value="/save-interest" />
<c:url var="deleteInterest" value="/delete-interest" />

<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<div class="row">

	<div class="col-md-10 col-md-offset-1">

		<div id="profile-name">
			<c:out value="${firstname}"/>'s profile
			<!--  &nbsp;<c:out value="${surname}"/>'s profile   -->
		</div>

		<div id="profile-photo-status"></div>

		<div id="interestDiv">
			<ul id="interestList">
				<c:choose>
					<c:when test="${empty profile.interests}">
						<li>Add your interests here (example: music)!</li>
					</c:when>
					<c:otherwise>
						<c:forEach var="interest" items="${profile.interests}">
							<li>${interest}</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>


		<div class="profile-about">
			<div class="profile-image">
				<div>
					<img src="${userPhotoUrl}" />
				</div>

				<div>
					<c:if test="${ownProfile == true}">

						<form action="upload" method="post" enctype="multipart/form-data">
							<input type="file" accept="*" name="file" /> <input
								type="submit" value="Upload File" /> <input type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
					</c:if>
				</div>
			</div>
				
		

			<div class="profile-text">
				<c:choose>
					<c:when test="${(profile.about == null) and (ownProfile == true)}">
				Click 'edit' to add information about yourself to your profile
				</c:when>
					<c:otherwise>
						${profile.about}
					</c:otherwise>
				</c:choose>
				
			</div>

			
		</div>

		<div class="profile-about-edit">
			<c:if test="${ownProfile == true}">
				<a href="${editProfileAbout}">edit</a>
			</c:if>
		</div>
	</div>




<script>
	
	function saveInterest(text) {
		editInterest(text, "${saveInterest}");
	}
	function deleteInterest(text) {
		editInterest(text, "${deleteInterest}");
	}
	function editInterest(text, actionUrl) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			jqXHR.setRequestHeader(header, token);
		});
		$.ajax({
			'url' : actionUrl,
			data : {
				'name' : text
			},
			type : 'POST',
			success : function() {
				//alert("Ok");
			},
			error : function() {
				//alert("error");
			}
		});
	}
	$(document).ready(function() {
		$("#interestList").tagit({
			afterTagRemoved : function(event, ui) {
				deleteInterest(ui.tagLabel);
			},
			afterTagAdded : function(event, ui) {
				if (ui.duringInitialization != true) {
					saveInterest(ui.tagLabel);
				}
			},
			caseSensitive : false,
			allowSpaces : true,
			tagLimit : 10,
			readOnly: '${ownProfile}' == 'false'
		});





		
	});
</script>