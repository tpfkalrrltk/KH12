<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹소켓</title>
</head>
<body>
<H1>웹소켓 실습 예졔</H1>


<!-- 로그인 화면 -->
<c:choose>
<c:when test="${sessionScope.name ==null}">
<form action="login" method="post">
ID <input type="text" name="memberId">
<br><br>
PW <input type="password" name="memberPw">
<br><br>
<button type="submit">로그인</button>
</form>
</c:when>
<c:otherwise>
<a href="logout">로그아웃</a>
</c:otherwise>
</c:choose>


<H2><a href="/default">웹소켓 기본</a></H2>
<H2><a href="/time">웹소켓 타임</a></H2>
<H2><a href="/group">웹소켓 그룹</a></H2>
<h2><a href="/member">회원 전용 웹소켓</a></h2>
<h2><a href="/json">회원 전용 웹소켓(+JSON)</a></h2>


</body>
</html>