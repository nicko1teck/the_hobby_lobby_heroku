<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pagi"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>



<%------------------ATTRIBUTES---------------------- --%>


<%-------------SPRING PAGE OBJECT--------------- --%>
<!-- we are doing this so we can make use of the page object here, like we did in viewstatus.jsp -->
<%@ attribute name="page" required="true"
	type="org.springframework.data.domain.Page"%>
	
	
	<%----------------BASE URL OF THE PAGE--------------- --%>
<%@ attribute name="url" required="true"%>

<!--  <p>Base URL contains question mark:  ${f:contains(url, '?')}</p>  -->

<c:set var="paramListSeparator" value="${f:contains(url, '?') ? '&' : '?'}" />
								
<!-- How many pages to display in one block -->
<%@ attribute name="size" required="false"%>


<%------------------ATTRIBUTE CALCULATIONS---------------- --%>

<!-- If size is empty, set it to 10, if not, set it to itself (WTF?) -->
<c:set var="size" value="${empty size ? 10: size}" />

<!-- This variable will track which block we're on, making use of the implicity param object -->
<c:set var="block" value="${empty param.b ? 0: param.b}" />
<!--
<p>block is: "${block}"</p>
<p>size is: "${size}"</p>
-->
<!-- I have no idea what the FUCK is going on here, John -->
	
	<c:set var="startPage" value="${block * size +1}" />
	<c:set var="endPage" value="${(block + 1) * size}" />
<!-- Now we have to allow for when endpage isn't a multiple of 5 -->
	
	<c:set var="endPage" value="${endPage > page.totalPages ? page.totalPages : endPage}" />
<!--
<p>startPage is: "${startPage}"</p>
<p>endPage is: "${endPage}"</p>
-->

<%------------------------OUTPUT--------------------- --%>

<c:if test="${page.totalPages != 1}">

<div class="pagination">

	<!-- If the block is not '0', display the back arrows
	<c:if test="${block != 0}">
		<a href="${url}${paramListSeparator}b=${block-1}&p=${(block-1)*size + 1}">&lt;&lt;</a>
	</c:if>
	 -->
	<c:if test="${block != 0}">
		<a href="${url}${paramListSeparator}b=${block-1}&p=${(block-1)*size + 1}">&lt;&lt;</a>
	</c:if>

	<c:forEach var="pageNumber" begin="${startPage}" end="${endPage}">

		<c:choose>

			<%-- the page numbers that are NOT the one we're on... ---%>
			<c:when test="${page.number != pageNumber -1}">

				<%-- hyperlink the page numbers --%>
				<a href="${url}${paramListSeparator}p=${pageNumber}&b=${block}"><c:out value=" ${pageNumber} " /></a>
												<%-- And we add the b=block to keep the block correct when we click a pagenumber  --%>		

			</c:when>
			<c:otherwise>
				<strong><c:out value=" ${pageNumber} " /> </strong>
			</c:otherwise>

		</c:choose>

		<c:if test="${pageNumber != endPage}"> | </c:if>

	</c:forEach>
	
	<!-- If the end page is not equal to the total pages, display the arrows 
	<c:if test="${endPage != page.totalPages}"><a href="?b=${block+1}&p=${(block+1)*size + 1}">&gt;&gt;</a>
	</c:if>
	
	-->
	
	<c:if test="${endPage != page.totalPages}">
		<a href="${url}${paramListSeparator}b=${block+1}&p=${(block+1)*size + 1}">&gt;&gt;</a>
</c:if>
	
</div>

</c:if>