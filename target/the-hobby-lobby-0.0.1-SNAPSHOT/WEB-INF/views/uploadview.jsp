<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>

<meta charset="UTF-8"></meta>

<title>Insert title here</title>

</head>
<body>

<form action="/upload" method="post" enctype="multipart/form-data">
	select photo:
	<input type="file" accept="*" name="file" />
	<input type="submit" value="Upload File" /> 
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>


</body>
</html>