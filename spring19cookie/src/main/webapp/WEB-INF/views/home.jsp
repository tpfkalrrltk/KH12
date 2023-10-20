<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>쿠키 테스트</h1>
	<%-- JSP에서는 EL을 이용하여 쿠키를 탐색할 수 있다
		EL의 내장객체 중에서 cookie 라는 이름의 내장객체가 존재하며 이를 활용한다
 --%>
	<h2>popup 쿠키 잇나요? - ${cookie.popup != null}</h2>

	<%-- 쿠키가 있을때만 나오는 화면 구현 --%>
	<c:if test="${cookie.popup ==null }">
	<img src="sss.png">
	</c:if>
	<h3>
		<a href="create1">평생 안보기</a>
	</h3>
	<h3>
		<a href="create2">1주일 안보기</a>
	</h3>
	<h3>
		<a href="create3">하루 안보기</a>
	</h3>
	<h3>
		<a href="create4">오늘 안보기</a>
	</h3>
	<h3>
		<a href="delete">그냥보기</a>
	</h3>
	<%-- saveId란 이름으로 아이디가 저장된 쿠키가 있을 경우 로그인 화면 --%>
	<form action="login" method="post">
		<input type="text" name="memberId" value="${cookie.saveId.value}">
		<br>
		<br> <input type="password" name="memberPw"> <br>
		<br>

		<c:choose>
			<c:when test="${cookie.saveId !=null}">
				<input type="checkbox" name="remember" checked value="ok"> 아이디 저장하기
  </c:when>
			<c:otherwise>
				<input type="checkbox" name="remember" > 아이디 저장하기
  </c:otherwise>
		</c:choose>

		<br>
		<br>
		<button type="submit">로그인</button>
	</form>
</body>
</html>