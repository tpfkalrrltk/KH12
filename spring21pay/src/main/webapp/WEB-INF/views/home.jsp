<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<h1>결재를 배웁시다</h1>
<c:choose>
	<c:when test="${sessionScope.name ==null}">
	<form action="login" method="post">
	<input type="text" name="memberId" placeholder="아이디"><br><br>
		<input type="password" name="memberPw" placeholder="비밀번호"><br><br>
		<button type="submit">로그인</button>
	</form>
	</c:when>
	<c:otherwise>
		<h1>${sessionScope.name}으로 로그인 중...</h1>
		<a href="logout">로그아웃</a>
	</c:otherwise>
</c:choose>

<h2>
	<a href="pay/test1">첫번째 예제</a>
</h2>

<h2>
	<a href="pay/test2">두번째 예제</a>
</h2>

<h2>
	<a href="pay/test3">세번째 예제</a>
</h2>

