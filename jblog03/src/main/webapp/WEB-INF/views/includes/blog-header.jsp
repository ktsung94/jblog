<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="menu">	
	<c:choose>
		<c:when test="${empty authUser }">
				<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a></li>
		</c:when>
		<c:otherwise>
				<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>
				<c:if test="${authUser.id eq blog.id }">
					<li><a href="${pageContext.servletContext.contextPath }/${blog.id}/admin/basic">블로그 관리</a></li>
				</c:if>
		</c:otherwise>
	</c:choose>	
</ul>